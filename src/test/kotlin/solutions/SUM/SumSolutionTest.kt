package solutions.SUM

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SumSolutionTest {

    @Test
    fun sum() {
        println("Hello");
        Assertions.assertEquals(2, SumSolution.sum(1, 1))
        Assertions.assertEquals(0, SumSolution.sum(-1, 1))
        Assertions.assertEquals(0, SumSolution.sum(7, 11))
    }
}