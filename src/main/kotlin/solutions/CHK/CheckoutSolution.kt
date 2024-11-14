package solutions.CHK

import java.io.File
import kotlin.random.Random


data class PackOffer (
    public val offerSize: Int,
    public val offerPrice: Int
)

data class FreeOffer (
    public val requireSize: Int,
    public val freeUnitCode: Char
)

data class UnitPrice (
    public val unitPrice: Int,
    public val packOffers: List<PackOffer>,
    public val freeOffer:FreeOffer?
)



object CheckoutSolution {
    fun checkout(skus: String): Int {
        /*
+------+-------+------------------------+
| Item | Price | Special offers         |
+------+-------+------------------------+
| A    | 50    | 3A for 130, 5A for 200 |
| B    | 30    | 2B for 45              |
| C    | 20    |                        |
| D    | 15    |                        |
| E    | 40    | 2E get one B free      |
| F    | 10    | 2F get one F free      |
+------+-------+------------------------+
        */

        val prices = mapOf(
            'A' to UnitPrice(50, listOf(PackOffer(5, 200), PackOffer(3, 130)), null),
            'B' to UnitPrice(30, listOf(PackOffer(2, 45)), null),
            'C' to UnitPrice(20, listOf(), null),
            'D' to UnitPrice(15, listOf(), null),
            'E' to UnitPrice(40, listOf(), FreeOffer(2, 'B')),
            'F' to UnitPrice(10, listOf(), FreeOffer(2, 'F')),
        );

        val checkout:MutableMap<Char, Int> = prices.keys.associateWith { 0 }.toMutableMap();

        for(unitCode in skus) {
            if (!checkout.containsKey(unitCode)) {
                return -1;
            } else {
                checkout[unitCode] = checkout[unitCode]!!+1;
            }
        }        

    
        var total = 0;

        for((unitCode, unitCount) in checkout) {
            val price = prices[unitCode]!!;
            if (price.freeOffer!=null) {
                val offer = price.freeOffer;
                if (unitCode==offer.freeUnitCode) {
                    val unitsFree = unitCount / (offer.requireSize+1);
                    checkout[offer.freeUnitCode] = unitCount-unitsFree;
                } else {
                    val unitsFree = unitCount / offer.requireSize;
                    checkout[offer.freeUnitCode] = maxOf(0, checkout[offer.freeUnitCode]!!-unitsFree);
                } 
            }
        }

        for((unitCode, unitCount) in checkout) {
            val price = prices[unitCode]!!;

            var unitsRemains:Int = unitCount;
            
            for(offer in price.packOffers) {
                val offerPacks = unitsRemains / offer.offerSize;
                total += offerPacks * offer.offerPrice;
                
                unitsRemains %= offer.offerSize
            }

            total += unitsRemains * price.unitPrice;
        }

        return total;
    }    
}