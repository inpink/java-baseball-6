package baseball.service.hint;

import baseball.domain.NumberBaseball;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BallHintServiceImplTest {

    private final BallHintServiceImpl ballHintServiceImpl = BallHintServiceImpl.getInstance();

    @Test
    void count_zeroBall() {
        //given
        NumberBaseball computerBaseball = NumberBaseball.createBaseball("123");
        NumberBaseball inputBaseball = NumberBaseball.createBaseball("456");

        //when
        int count = ballHintServiceImpl.count(computerBaseball, inputBaseball);

        //then
        assertThat(count).isZero();
    }

    @Test
    void count_zeroBall_samePlace() {
        //given
        NumberBaseball computerBaseball = NumberBaseball.createBaseball("123");
        NumberBaseball inputBaseball = NumberBaseball.createBaseball("156");

        //when
        int count = ballHintServiceImpl.count(computerBaseball, inputBaseball);

        //then
        assertThat(count).isZero();
    }

    @Test
    void count_oneBall() {
        //given
        NumberBaseball computerBaseball = NumberBaseball.createBaseball("123");
        NumberBaseball inputBaseball = NumberBaseball.createBaseball("451");

        //when
        int count = ballHintServiceImpl.count(computerBaseball, inputBaseball);

        //then
        assertThat(count).isOne();
    }
}