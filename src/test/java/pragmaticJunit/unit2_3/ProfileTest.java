package pragmaticJunit.unit2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileTest {
    private Profile profile;
    private Question question;
    private Criteria criteria;

    /**
     * 매 테스트 전 초기화
     */
    @Before
    public void create(){
        profile = new Profile("Bull Hockey, Inc");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }


    /**
     * 이 테스트 케이스는 Weigth.DONCARE가 아니기 때문에 항상 MATCH 가 FALSE 로 나오게되고 (30행)
     * Wegith.MustMatch이기 때문에 kill이 true가 되면 false가 반환된다.
     */
    @Test
    public void matchAnswerFalseWhenMustMatchCriteriaNotMet(){ //이 테스트 케이스는
        //준비
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

        //실행
        boolean matches = profile.matches(criteria);

        //단언
        assertFalse(matches); //보너스를 못받았다고 했으니까
    }

    /**
     * Weight.DonCare이기때문에 match가 항상 true이고, return ture가 된다.
     */
    @Test
    public void matchAnswersTrueForAnyDontCareCriteria(){
        //준비 3 - 예상 답?
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

        //실행
        boolean matches = profile.matches(criteria);

        //단언
        assertTrue(matches);
    }

}