package pragmaticJunit.unit1_4;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ScoreCollectionTest {

    @Test //Test 어노테이션은 org.junit 패키지에 있음
    public void test(){
        //fail("Not yet Implemented"); //Fail 정적 메서드는 org.junit.Assert 클래스에 있음
        //java.lang.AssertionError: Not yet Implemented
    }

    @Test
    public void answerArithmeticMeanOfTwoNumbers() {
        //준비 : 테스트에서 어떤 것을 하기 위해서는 먼저 테스트 상태 설정
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        //실행 : 검증하려는 코드인 arithmeticMean 메서드 실행
        int actualResult = collection.arithmeticMean();

        //단언 : 기대하는 결과를 assert
        assertThat(actualResult, equalTo(6)); //assertThat(실제 결과, Matcher 객체)

    }

}