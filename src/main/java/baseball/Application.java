package baseball;

import baseball.controller.GameController;
import baseball.controller.RestartController;
import baseball.domain.Restart;
import baseball.domain.GameConstants;
import baseball.domain.Baseball;
import baseball.util.ExceptionUtil;
import baseball.util.InputUtil;
import baseball.util.IntegerListUtil;
import baseball.util.IntegerUtil;
import baseball.view.EndView;
import baseball.view.GameView;
import baseball.view.RestartView;
import baseball.view.StartView;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

public class Application {
    public static void main(String[] args) {

    TotalGameLoop:
        while (true) {
            StartView.displayStartMessage();

            //컴퓨터는 서로 다른 숫자로 이루어진 3자리 숫자 1개를 뽑음　
            Baseball computerBaseball = new Baseball();
            List<Integer> computerNumList = GameController.generateRandomBaseball();
            computerBaseball.setValues(computerNumList);
            System.out.println("컴퓨터가 뽑은 수" + computerNumList);

            GuessingLoop:
            while (true) {
                //사용자는 서로 다른 숫자로 이루어진 3자리 숫자 1개를 입력　
                InputView.displayInputMessage();
                String inputNum = Console.readLine();
                String inputNum = InputUtil.inputString();

                //사용자가 잘못된 값을 입력하면 IllegalArgumentException을 발생시킨 후 애플리케이션이 종료된다.
                //검증: 만약 숫자가 아닌 것을 입력하면 잘못 입력한 것이다.
                if (!IntegerUtil.isInteger(inputNum)) {
                    ExceptionUtil.throwInvalidValueException();
                }

                //검증: 만약 숫자의 자리수가 3보다 작으면 잘못 입력한 것이다.
                //검증: 만약 숫자의 자리수가 3보다 크면 잘못 입력한 것이다.
                int inputNumLen = inputNum.length();
                if (inputNumLen < GameConstants.NUMBER_LENGTH) {
                    ExceptionUtil.throwInvalidValueException();
                }

                if (inputNumLen > GameConstants.NUMBER_LENGTH) {
                    ExceptionUtil.throwInvalidValueException();
                }

                //검증: 만약 같은 숫자가 2개 이상 있으면 잘못 입력한 것이다.
                Set numSet = new HashSet();
                for (int i = 0; i < GameConstants.NUMBER_LENGTH; i++) {
                    numSet.add(inputNum.charAt(i));
                }
                if (numSet.size() < GameConstants.NUMBER_LENGTH) {
                    ExceptionUtil.throwInvalidValueException();
                }

                //검증: 만약 숫자의 각 자리가 1이상 9이하가 아니면 잘못 입력한 것
                for (int i = 0; i < GameConstants.NUMBER_LENGTH; i++) {
                    if (!inputNum.matches("^[1-9]+$")) {
                        ExceptionUtil.throwInvalidValueException();
                    }
                }

                //검증이 완료되어야 변환 가능
                List<Integer> inputNumList = parseIntegerList(inputNum);
                System.out.println(inputNumList);
                List<Integer> inputNumList = IntegerListUtil.parseIntegerList(inputNum);

                //컴퓨터는 사용자가 입력한 숫자
                // 와 뽑은 숫자를 비교하여 힌트를 판단, 출력　
                //힌트는 스트라이크, 볼, 낫싱 3가지　
                //같은 수가 같은 자리에 있으면 스트라이크　
                int strike = 0;
                for (int i = 0; i < GameConstants.NUMBER_LENGTH; i++) {
                    if (inputNumList.get(i).equals(computerNumList.get(i))) {
                        strike++;
                    }
                }

                //같은 수가 다른 자리에 있으면 볼　
                int ball = 0;
                for (int i = 0; i < GameConstants.NUMBER_LENGTH; i++) {
                    for (int j = 0; j < GameConstants.NUMBER_LENGTH; j++) {
                        if (i == j) {
                            continue;
                        }
                        if (inputNumList.get(i).equals(computerNumList.get(j))) {
                            ball++;
                        }
                    }
                }

                //같은 수가 전혀 없으면 낫싱　
                boolean nothing = false;
                if (strike == 0 && ball == 0) nothing = true;

                //힌트 출력 화면
                //형식은 "숫자+힌트명"이며, 여러 개일 경우 공백 하나를 기준으로 한 줄에 출력
                StringBuilder hintBuilder = new StringBuilder();
                if (ball > 0) {
                    hintBuilder.append(ball + "볼");
                }

                if (!hintBuilder.isEmpty()) { //비어있지 않으면 공백 넣기
                    hintBuilder.append(" ");
                }

                if (strike > 0) {
                    hintBuilder.append(strike + "스트라이크");
                }

                if (nothing) {
                    hintBuilder.append("낫싱");
                }

                System.out.println(hintBuilder.toString());

                //[게임 종료]
                //사용자가 입력한 숫자와 컴퓨터가 뽑은 숫자가 모두 같으면 "게임 종료 문구" 출력 후 게임 종료　
                if (strike == GameConstants.NUMBER_LENGTH) {
                    EndView.displayEndMessage();
                    RestartView.displayRestartChoiceMessage();
                    String regameNum = InputUtil.inputString();

                    //사용자가 잘못된 값을 입력하면 IllegalArgumentException을 발생시킨 후 애플리케이션이 종료된다.
                    //검증: 만약 숫자가 아닌 것을 입력하면 잘못 입력한 것이다.
                    if (!IntegerUtil.isInteger(regameNum)) {
                        ExceptionUtil.throwInvalidValueException();
                    }
                    int parsedRegameNum = Integer.parseInt(regameNum);

                    Restart restart = RestartController.validateValue(parsedRegameNum);

                    if (restart == Restart.RESTART_GAME){
                        break GuessingLoop;
                    }
                    if (restart == Restart.EXIT_GAME) {
                        return;
                    }
                    if (restart == Restart.OTHER_CHOICE) {
                        ExceptionUtil.throwInvalidValueException();
                    }
                }
            }
        }
    }


    //String to List<Integer> TODO: IntegerListConverter 따로 둬서 IntegerListConverter.parseIntegerList
    public static List<Integer> parseIntegerList(String string){
        List<Integer> integerList = new ArrayList<>();

        for (char character : string.toCharArray()){
            integerList.add(IntegerUtil.parseInteger(character));
        }

        return integerList;
    }
}