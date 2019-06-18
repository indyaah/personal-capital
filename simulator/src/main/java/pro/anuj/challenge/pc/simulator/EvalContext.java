package pro.anuj.challenge.pc.simulator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class EvalContext {

    private Integer simulationCount;
    private Integer numberOfYears;
    private Double inflation;
}
