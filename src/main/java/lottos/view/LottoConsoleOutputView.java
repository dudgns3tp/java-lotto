package lottos.view;

import lottos.domain.Lotto;
import lottos.domain.LottoGameStatistics;
import lottos.domain.LottoPrize;
import lottos.domain.Lottos;
import lottos.domain.numbers.Number;
import lottos.domain.numbers.Numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class LottoConsoleOutputView {

    public void printLottos(final Lottos lottos) {
        for (Lotto lotto : lottos.elements()) {

            Numbers numbers = lotto.getNumbers();
            final List<Integer> values = numbers.elements()
                    .stream()
                    .map(Number::value)
                    .collect(Collectors.toList());

            System.out.println(values);
        }
        System.out.println();
    }

    public void printPurchaseCount(final Lottos lottos) {
        int purchaseCount = lottos.elements().size();
        System.out.printf("%d개를 구매했습니다.%n", purchaseCount);
    }

    private void printIntroStatistics() {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("----------");
    }

    public void printStatistics(final LottoGameStatistics lottoGameStatistics) {
        printIntroStatistics();
        printCountOfMatches(lottoGameStatistics);
        printYield(lottoGameStatistics);
    }

    private void printCountOfMatches(final LottoGameStatistics lottoGameStatistics) {
        for (LottoPrize winningLottoPrize : LottoPrize.winningValues()) {
            printCountOfMatches(lottoGameStatistics.groupingByPrize(), winningLottoPrize);
        }
    }

    private void printCountOfMatches(Map<LottoPrize, List<LottoPrize>> prizeMap, LottoPrize winningLottoPrize) {
        final int prize = winningLottoPrize.getPrizeAmount();
        final int countOfMatches = winningLottoPrize.getCountOfMatches();
        final List<LottoPrize> lottoMatchers = prizeMap.getOrDefault(winningLottoPrize, new ArrayList<>());

        if (winningLottoPrize.isBonus()) {
            System.out.printf("%d개 일치 보너스 볼 일치(%d원) - %d개%n", countOfMatches, prize, lottoMatchers.size());
            return;
        }
        System.out.printf("%d개 일치 (%d원) - %d개%n", countOfMatches, prize, lottoMatchers.size());
    }

    private void printYield(final LottoGameStatistics lottoGameStatistics) {
        Double yield = lottoGameStatistics.calculateYield();
        System.out.printf("수익률은 %.2f입니다.", yield);
    }
}