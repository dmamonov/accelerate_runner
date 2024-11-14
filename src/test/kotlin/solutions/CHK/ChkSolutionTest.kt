package solutions.CHK

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ChkSolutionTest {

/**
+------+-------+------------------------+
| Item | Price | Special offers         |
+------+-------+------------------------+
| A    | 50    | 3A for 130, 5A for 200 |
| B    | 30    | 2B for 45              |
| C    | 20    |                        |
| D    | 15    |                        |
| E    | 40    | 2E get one B free      |
+------+-------+------------------------+
 */

    @Test
    fun bill() {
        Assertions.assertEquals(0, CheckoutSolution.checkout(""))
        Assertions.assertEquals(175, CheckoutSolution.checkout("AAABB"))
        Assertions.assertEquals(15, CheckoutSolution.checkout("D"))
        Assertions.assertEquals(100+45+30+40, CheckoutSolution.checkout("BABDDCAC"))
        Assertions.assertEquals(-1, CheckoutSolution.checkout("-"))
        Assertions.assertEquals(80, CheckoutSolution.checkout("BEE"))
        Assertions.assertEquals(250, CheckoutSolution.checkout("AAAAAA"))
    }
}