/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package pragmaticJunit.unit2;

import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.hamcrest.number.IsCloseTo.*;
// ...

public class AssertHamcrestTest {
   @Test
   @Ignore
   @ExpectToFail
   public void assertDoublesEqual() {
      assertThat(2.32 * 3, equalTo(6.96)); //6.959999999999999
   }

   /*
      위의 테스트 케이스에서 float, double을 비교할때 공차/허용오차를 지정한다.
    */
   @Test
   public void assertWithTolerance() {
      assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
   }

   /*
      위의 테스트는 절대값을 구해서 true인지 확인했는데, 이 테스트는 hamcrest에 있는 isCloseTo를 사용해서 가독성이 더 좋음
      closeTo(double 값, doueble 오차허용범위) -> 즉 2.32 * 3이 6.9595 ~ 6.9605 사이인지 물어봄
    */
   @Test
   public void assertDoublesCloseTo() {
      assertThat(2.32 * 3, closeTo(6.96, 0.0005));
   }
}
