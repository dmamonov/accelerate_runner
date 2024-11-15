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
    private val prices = mapOf(
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
        'K' to UnitPrice(70, listOf(PackOffer(2,120)), null),
        'L' to UnitPrice(90, listOf(), null),
        'M' to UnitPrice(15, listOf(), null),
        'N' to UnitPrice(40, listOf(), FreeOffer(3, 'M')),
        'O' to UnitPrice(10, listOf(), null),
        'P' to UnitPrice(50, listOf(PackOffer(5,200)), null),
        'Q' to UnitPrice(30, listOf(PackOffer(3,80)), null),
        'R' to UnitPrice(50, listOf(), FreeOffer(3, 'Q')),
        'S' to UnitPrice(20, listOf(), null), 
        'T' to UnitPrice(20, listOf(), null), 
        'U' to UnitPrice(40, listOf(), FreeOffer(3,'U')),
        'V' to UnitPrice(50, listOf(PackOffer(3, 130), PackOffer(2, 90)), null),
        'W' to UnitPrice(20, listOf(), null),
        'X' to UnitPrice(17, listOf(), null),
        'Y' to UnitPrice(20, listOf(), null),
        'Z' to UnitPrice(21, listOf(), null),
    );

    //note [DM] this must be sorted by price, descending.
    private val groupDiscounts = listOf(listOf(
        'Z'/*£21*/,
        'T'/*£20*/,
        'Y'/*£20*/,
        'S'/*£20*/,
        'X'/*£17*/,
    ) to PackOffer(3,45))

    fun checkout(skus: String): Int {
        val checkout:MutableMap<Char, Int> = prices.keys.associateWith { 0 }.toMutableMap();
        for(unitCode in skus) {
            if (!checkout.containsKey(unitCode)) {
                return -1;
            } else {
                checkout[unitCode] = checkout[unitCode]!!+1;
            }
        }        

    
        var total = 0;

        run { //apply "group" discounts:
            for ((unitCodeList, offer) in groupDiscounts) {
                var groupSize:Int = 0;
                for(unitCode in unitCodeList) {
                    groupSize+=checkout[unitCode]!!;
                }
                val discountedPacks = groupSize/offer.offerSize;
                total += discountedPacks*offer.offerPrice;
    
                var remainsToRemove:Int = discountedPacks*offer.offerSize;
                for(unitCode in unitCodeList) {
                    val unitSize = checkout[unitCode]!!;
                    val reduceSize = minOf(remainsToRemove, unitSize);
                    if (reduceSize>0) {
                        remainsToRemove-=reduceSize;
                        checkout[unitCode]=unitSize-reduceSize;
                    }
                }
            }
        }

        run { //apply "free" discounts:
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
        }

        run { //apply "pack" discounts:
            for((unitCode, unitCount) in checkout) {
                val price = prices[unitCode]!!;

                var unitsRemains:Int = unitCount;
                
                for(offer in price.packOffers) {
                    val offerPacks = unitsRemains / offer.offerSize;
                    total += offerPacks * offer.offerPrice;
                    
                    unitsRemains %= offer.offerSize
                }

                checkout[unitCode] = unitsRemains;
            }
        }

        run { //apply regular price:
            for((unitCode, unitCount) in checkout) {
                val price = prices[unitCode]!!;
                total += unitCount * price.unitPrice;
            }
        }


        return total;
    }    
}