/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package pragmaticJunit.unit2;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static pragmaticJunit.unit2.PointMatcher.isNear;

import java.io.*;
import java.util.*;
import org.junit.*;
// ...
import org.junit.rules.*;
// ...

/*
hamcrest의 CoreMachers 사용한 내역
1. equalTo
2. startsWith
 */
public class AssertTest {

   class InsufficientFundsException extends RuntimeException {
      public InsufficientFundsException(String message) {
         super(message);
      }

      private static final long serialVersionUID = 1L;
   }

   class Account {
      int balance;
      String name;

      Account(){
         throw new NullPointerException();
      }
      Account(String name) {
         this.name = name;
      }

      void deposit(int dollars) {
         balance += dollars;
      }

      void withdraw(int dollars) {
         if (balance < dollars) {
            throw new InsufficientFundsException("balance only " + balance);
         }
         balance -= dollars;
      }

      public String getName() {
         return name;
      }

      public int getBalance() {
         return balance;
      }

      public boolean hasPositiveBalance() {
         return balance > 0;
      }
   }

   class Customer {
      List<Account> accounts = new ArrayList<>();

      void add(Account account) {
         accounts.add(account);
      }

      Iterator<Account> getAccounts() {
         return accounts.iterator();
      }
   }

   private Account account;

   @Before
   public void createAccount() {
      account = new Account("an account name");
   }


   @Test
   public void hasPositiveBalance() {
      account.deposit(50);
      assertTrue(account.hasPositiveBalance());
   }


   @Test
   public void depositIncreasesBalance() {
      int initialBalance = account.getBalance();
      account.deposit(100);
      assertTrue(account.getBalance() > initialBalance); //Junit
      assertThat(account.getBalance(), equalTo(100)); //hamcrest : assertThat(actual, expected)

      /* fail case로 junit의 assertTrue 보다 assertThat이 자세하게 나온다. hamcrest는 acutal 과 expected를 로그에 보여줌. */
//      assertTrue(account.getBalance() < initialBalance);
//      assertThat(account.getBalance(), equalTo(1001));

   }

   
   @Test
   public void depositIncreasesBalance_hamcrestAssertTrue() {
      account.deposit(50);
      assertThat(account.getBalance() > 0, is(true));
   }
   
   @Ignore
   @ExpectToFail
   @Test
   public void comparesArraysFailing() {
      assertThat(new String[] {"a", "b", "c"}, equalTo(new String[] {"a", "b"}));
   }

   @Test
   public void comparesArraysPassing() {
      assertThat(new String[] {"a", "b"}, equalTo(new String[] {"a", "b"}));
   }

   @Ignore
   @ExpectToFail
   @Test
   public void comparesCollectionsFailing() {
      assertThat(Arrays.asList(new String[] {"a"}), 
            equalTo(Arrays.asList(new String[] {"a", "ab"})));
   }

   @Test
   public void comparesCollectionsPassing() {
      assertThat(Arrays.asList(new String[] {"a"}), 
            equalTo(Arrays.asList(new String[] {"a"})));
   }
   
   @Ignore
   @Test
   public void testWithWorthlessAssertionComment() {
      account.deposit(50);
      assertThat("account balance is 100", account.getBalance(), equalTo(50));
      //assertThat에 message는 단언의 근거를 설명해 주고, 테스트 실패시 해당 message가 출력이 된다 .
      //하지만 여기서 예 처럼 구현 세부 사항을 설명하는 주석이 코드와 일치하지 않는 경우 오해르 일으킬 수 있음.
      //따라서, 메세지를 넣는 것도 좋지만 그것보다 테스트 코드 자체가 이해하기 쉽게 만들거나 의미있는 상수 도입, 테스트의 이름 변경 등으로 개선 할 수 있다.
   }

   @Test
   @ExpectToFail
   @Ignore
   public void assertFailure() {
      assertTrue(account.getName().startsWith("xyz"));
   }

   @Test
   @ExpectToFail
   @Ignore
   public void matchesFailure() {
      assertThat(account.getName(), startsWith("xyz"));
   }

   @Test
   public void variousMatcherTests() {
      Account account = new Account("my big fat acct");

      assertThat(account.getName(), is(equalTo("my big fat acct"))); //is 는 그냥 받은 matcher의 값을 돌려주는 역할만 한다. is(equalTo()) 나 equalTo()동일함

      assertThat(account.getName(), allOf(startsWith("my"), endsWith("acct"))); //allOf 는 n개의 matchers가 맞는지 본다. and 연산자처럼

      assertThat(account.getName(), anyOf(startsWith("my"), endsWith("loot"))); //anyOf는 or 연산자와 같음

      assertThat(account.getName(), not(equalTo("plunderings")));
      assertThat(account.getName(), is(not(equalTo("plunderings"))));

      /*
         예외를 던지는 null 참조 예외 테스트는 오류가 발생하며, 테스트 실패는 발생하지 않는다. Junit은 발생한 예외를 테스트 코드에서 잡지 않는 경우 오류를 보고 한다.
       */
      assertThat(account.getName(), is(not(nullValue())));
      assertThat(account.getName(), is(notNullValue()));


      assertThat(account.getName(), isA(String.class));

      assertThat(account.getName(), is(notNullValue())); // not helpful
      assertThat(account.getName(), equalTo("my big fat acct"));

   }

   @Test
   public void sameInstance() {
      Account a = new Account("a");
      Account aPrime = new Account("a");
      // TODO why needs to be fully qualified??
      assertThat(a, not(org.hamcrest.CoreMatchers.sameInstance(aPrime)));
   }

   @Test
   public void moreMatcherTests() {
      Account account = new Account(null);
      assertThat(account.getName(), is(nullValue()));
   }

   @Test
   @SuppressWarnings("unchecked")
   public void items() {
      List<String> names = new ArrayList<>();
      names.add("Moe");
      names.add("Larry");
      names.add("Curly");

      assertThat(names, hasItem("Curly"));

      assertThat(names, hasItems("Curly", "Moe"));

      assertThat(names, hasItem(endsWith("y")));

      assertThat(names, hasItems(endsWith("y"), startsWith("C"))); //warning!

      assertThat(names, not(everyItem(endsWith("y"))));
   }

   @Test
   @ExpectToFail @Ignore
   public void location() {
      Point point = new Point(4, 5);
      
      // WTF why do JUnit matches not include closeTo
//      assertThat(point.x, closeTo(3.6, 0.2));
//      assertThat(point.y, closeTo(5.1, 0.2));
      
      assertThat(point, isNear(3.6, 5.1));
   }
   
   @Test
   @ExpectToFail @Ignore
   public void classicAssertions() {
      Account account = new Account("acct namex");
      assertEquals("acct name", account.getName());
   }

   /*
   ========================================== junit 예외 테스트 방법 ===================================================
   1. @Test의 속성으로 expected = 예외클래스 를 사용하고, 실제 예외가 발생하게 할 수 있는 코드를 구현한다.
   2. try/catch 사용 하여, 에러가 발생하는 상황에서 fail()을 호출하여 강제로 해당 exception을 발생시켜준다
   3. Junit 커스텀 규칙 사용 - 관점지향프로그래밍인 (AOP, Aspect Oriented Programming)과 유사한 기능을 제공한다.  => @Rule 사용
   4. Fishbowl : http://stefanbirkner.github.io/fishbowl/index.html, https://www.codeaffine.com/2014/07/28/clean-junit-throwable-tests-with-java-8-lambdas/
   5. 검증된 예외를 처리하력 테스트코드에 try/catch 블럭을 넣지 말고 throw한다. 예를 들면,  BufferedWriter 객체를 사용할 때 IOException을 잡아 주어야 하는데 테스트 코드에서는 그냥 throw시킨다
    */


   /*
   junit문제에서 객체가 파라미터 없이 생성되었을 때 nullpointer
    */
   @Test(expected = NullPointerException.class)
   public void throwsNullPointerWhenAccountCreatedWithoutParam(){
      Account account = new Account();
   }

   /*
    * Test 어노테이션의 속성으로 기대하는 예외를 지정할 수 있다.
    * 이 테스트의 경우 InsufficientFundsException이 발생하지 않으면 실패하는 케이스이고 에러는 다음과 같음 (돈을 안뽑거나 0원을 뽑으면 실패)
    * java.lang.AssertionError: Expected exception: pragmaticJunit.unit2.AssertTest$InsufficientFundsException
    */
   @Test(expected=InsufficientFundsException.class)
   public void throwsWhenWithdrawingTooMuch() {
      account.withdraw(100);
   }

   /*
   try/catch를 사용해서 try block에서 강제로 fail()을 호출한다. fail이 호출되면 catch로 제어가 넘어가게 되고, 테스트가 종료(통과)된다.
      ** fail()을 해주지 않으면 오류는 throw 가 되어도 테스트 실패는 발생하지 않는다.
      ** 비어있는 catch블럭 허용됨
    */
   @Test
   public void throwsWhenWithdrawingTooMuchTry() {
      try {
         account.withdraw(100);
         fail();
      }
      catch (InsufficientFundsException expected) {
         assertThat(expected.getMessage(), equalTo("balance only 0"));
      }
   }

   @Test
   public void throwsNullPointerWhenAccountCreatedWithoutParam2(){
      try {
         Account account = new Account();
         fail();

      } catch (NullPointerException thrown) {

      }
   }

   /*
      검증된 예외를 처리하력 테스트코드에 try/catch 블럭을 넣지 말고 throw한다. 
    */
   @Test
   public void readsFromTestFile() throws IOException {
      String filename = "test.txt";
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      writer.write("test data");
      writer.close();
      // ...
   }
   
   @After
   public void deleteForReadsFromTestFile() {
      new File("test.txt").delete();
   }
   
   @Test
   @Ignore("don't forget me!")
   public void somethingWeCannotHandleRightNow() {
      // ...
   }

   /*
    * junit 규칙을 자동으로 테스트 집합에 종단 관심사 (cross-cutting concern : 불변성을 유지하는 관심사)를 부착할 수 있음
    * Junit안에 바로 사용할 수있는 몇가지 규칙들이 있는데 그 중 하나가 ExpectedException 이다.
    *    ExpectedException 사용 방법
    *       1. 테스트 클래스에 ExpectedException을 public 으로 선언하고 @Rule 어노테이션을 부착해야한다.
    *       2. 테스트 케이스에 발생할 수 있는 규칙을 thrown인스턴스를 사용해 알려준다.
    *             thrown.expect(예외클래스);
    *             thrown.expectMessage(예외 발생시 메세지);
    *       3. 마지막으로 예외를 발생시키는 코드를 구현한다.
    *
    */
   @Rule
   public ExpectedException thrown = ExpectedException.none();  
   
   @Test
   public void exceptionRule() {
      thrown.expect(InsufficientFundsException.class); 
      thrown.expectMessage("balance only 0");  
      
      account.withdraw(100);  
   }

   @Test
   public void throwsNullPointerWhenAccountCreatedWithoutParam3(){
      thrown.expect(NullPointerException.class);
      Account account = new Account();
   }

   
   @Test
   public void doubles() {
      assertEquals(9.7, 10.0 - 0.3, 0.005);
   }
}
