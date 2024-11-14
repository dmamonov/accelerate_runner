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
| G    | 20    |                        |
| H    | 10    | 5H for 45, 10H for 80  |
| I    | 35    |                        |
| J    | 60    |                        |
| K    | 80    | 2K for 150             |
| L    | 90    |                        |
| M    | 15    |                        |
| N    | 40    | 3N get one M free      |
| O    | 10    |                        |
| P    | 50    | 5P for 200             |
| Q    | 30    | 3Q for 80              |
| R    | 50    | 3R get one Q free      |
| S    | 30    |                        |
| T    | 20    |                        |
| U    | 40    | 3U get one U free      |
| V    | 50    | 2V for 90, 3V for 130  |
| W    | 20    |                        |
| X    | 90    |                        |
| Y    | 10    |                        |
| Z    | 50    |                        |
+------+-------+------------------------+
        */

        val prices = mapOf(
            'A' to UnitPrice(50, listOf(PackOffer(5, 200), PackOffer(3, 130)), null),
            'B' to UnitPrice(30, listOf(PackOffer(2, 45)), null),
            'C' to UnitPrice(20, listOf(), null),
            'D' to UnitPrice(15, listOf(), null),
            'E' to UnitPrice(40, listOf(), FreeOffer(2, 'B')),
            'F' to UnitPrice(10, listOf(), FreeOffer(2, 'F')),
            'G' to UnitPrice(20, listOf(), null),
            'H' to UnitPrice(10, listOf(PackOffer(10, 80), PackOffer(5, 45)), null),
            'I' to UnitPrice(35, listOf(), null),
            'J' to UnitPrice(60, listOf(), null),
            'K' to UnitPrice(80, listOf(PackOffer(2,150)), null),
            'L' to UnitPrice(90, listOf(), null),
            'M' to UnitPrice(15, listOf(), null),
            'N' to UnitPrice(40, listOf(), FreeOffer(3, 'M')),
            'O' to UnitPrice(10, listOf(), null),
            'P' to UnitPrice(50, listOf(PackOffer(5,200)), null),
            'Q' to UnitPrice(30, listOf(PackOffer(3,80)), null),
            'R' to UnitPrice(50, listOf(), FreeOffer(3, 'Q')),
            'S' to UnitPrice(30, listOf(), null),
            'T' to UnitPrice(20, listOf(), null),
            'U' to UnitPrice(40, listOf(), FreeOffer(3,'U')),
            'V' to UnitPrice(50, listOf(PackOffer(3, 130), PackOffer(2, 90)), null),
            'W' to UnitPrice(20, listOf(), null),
            'X' to UnitPrice(90, listOf(), null),
            'Y' to UnitPrice(10, listOf(), null),
            'Z' to UnitPrice(50, listOf(), null),
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