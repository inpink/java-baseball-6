package baseball.service.hint;

import baseball.domain.NumberBaseball;
import baseball.domain.GameConstants;
import baseball.domain.hint.Ball;
import baseball.domain.hint.HintItem;
import baseball.util.ObjectUtil;

import java.util.List;

public class BallHintServiceImpl implements HintItemService {

    private static final BallHintServiceImpl ballService = new BallHintServiceImpl();

    public static BallHintServiceImpl getInstance() {
        return ballService;
    }

    private BallHintServiceImpl() {

    }

    @Override
    public HintItem create(NumberBaseball computerBaseball, NumberBaseball inputBaseball) {
        int count = count(computerBaseball, inputBaseball);
        HintItem ball = new Ball(count);

        return ball;
    }

    @Override
    public int count(NumberBaseball computerBaseball, NumberBaseball inputBaseball) {
        int count = 0;
        List computer = computerBaseball.getValues();
        List input = inputBaseball.getValues();

        for (int i = 0; i < GameConstants.NUMBER_LENGTH; i++) {
            for (int j = 0; j < GameConstants.NUMBER_LENGTH; j++) {
                if (i == j) {
                    continue;
                }
                if (ObjectUtil.isSame(computer.get(i), input.get(j))) { //TODO: BaseBall 역할로 분리하기
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public boolean isActive(HintItem hintItem) {
        return hintItem.isActive();
    }


}
