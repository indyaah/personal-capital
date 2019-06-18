package pro.anuj.challenge.pc.simulator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class Portfolio {

    private float meanReturn;
    private float risk;
    private double assetValue;
    private PortfolioType type;
}
