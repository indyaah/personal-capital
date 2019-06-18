package pro.anuj.challenge.pc.simulator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class SimulationResult {
    public Double worstCase;
    public Double median;
    public Double bestCase;
    public PortfolioType portfolioType;

    public String toString() {
        return String.format("Risk Profile: %20s, Worst Case : %.4f, Median: %.4f, Best Case: %.4f", portfolioType, worstCase, median, bestCase);
    }

}

