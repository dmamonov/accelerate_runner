package solutions.CHK

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ChkSolutionTest {
    @Test
    fun bill() {
        Assertions.assertEquals(0, CheckoutSolution.checkout(""))
        Assertions.assertEquals(175, CheckoutSolution.checkout("AAABB"))
        Assertions.assertEquals(15, CheckoutSolution.checkout("D"))
        Assertions.assertEquals(100+45+30+40, CheckoutSolution.checkout("BABDDCAC"))
        Assertions.assertEquals(-1, CheckoutSolution.checkout("-"))
        Assertions.assertEquals(80, CheckoutSolution.checkout("BEE"))
        Assertions.assertEquals(250, CheckoutSolution.checkout("AAAAAA"))
        Assertions.assertEquals(20, CheckoutSolution.checkout("FF"))
        Assertions.assertEquals(20, CheckoutSolution.checkout("FFF"))
        Assertions.assertEquals(20, CheckoutSolution.checkout("FF"))
        Assertions.assertEquals(30, CheckoutSolution.checkout("FFFF"))
        Assertions.assertEquals(45, CheckoutSolution.checkout("YZZ"))
        Assertions.assertEquals(62, CheckoutSolution.checkout("XYZZ"))
        Assertions.assertEquals(79, CheckoutSolution.checkout("XXYZZ"))
        /*
         - {"method":"checkout","params":["S"],"id":"CHK_R5_021"}, expected: 20, got: 29
         - {"method":"checkout","params":["ABCDEFGHIJKLMNOPQRSTUVW"],"id":"CHK_R5_033"}, expected: 795, got: 804
         - {"method":"checkout","params":["SSSZ"],"id":"CHK_R5_142"}, expected: 65, got: 66
        */
        Assertions.assertEquals(20, CheckoutSolution.checkout("S"))
        Assertions.assertEquals(795, CheckoutSolution.checkout("ABCDEFGHIJKLMNOPQRSTUVW"))
        Assertions.assertEquals(65, CheckoutSolution.checkout("SSSZ"))
        
    }
}