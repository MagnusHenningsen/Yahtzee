package prosjektGruppe5.Utilities;

import com.beust.ah.A;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PointsCalculatingUtil {

    public Integer getRightAmountOfPoints(int[] diceValues,Integer currentRound)
    {
        int[] lowerStraight = {1,2,3,4,5};
        int[] higherStraight = {2,3,4,5,6};
        switch (currentRound)
        {
            case 0: //There are no rounds stored yet, which means this is my first round
                return calculateSumOfValues(diceValues,1);
            case 1:
                return calculateSumOfValues(diceValues,2);
            case 2:
                return calculateSumOfValues(diceValues,3);
            case 3:
                return calculateSumOfValues(diceValues,4);
            case 4:
                return calculateSumOfValues(diceValues,5);
            case 5:
                return calculateSumOfValues(diceValues,6);
            case 6: //Ett par started
                return calculateOfAKind(diceValues,2,1);
            case 7: //To par
                return calculateOfAKind(diceValues,2,2);
            case 8:
                return calculateOfAKind(diceValues,3,1);
            case 9:
                return calculateOfAKind(diceValues,4,1);
            case 10: //Liten Straight
                return calculatingStraight(diceValues, lowerStraight);
            case 11: // Stor Straight
                return calculatingStraight(diceValues, higherStraight);
            case 12: // Hus
               if(calculateOfAKind(diceValues,2,1) > 0 &&  calculateOfAKind(diceValues,3,1) > 0)
               {
                   return (calculateOfAKind(diceValues,2,1) + calculateOfAKind(diceValues,3,1));
               }else return 0;
            case 13://Sjanse
                return Arrays.stream(diceValues).sum();
            case 14: // Yatzy
                if(calculateOfAKind(diceValues,5,1) > 0)
                {
                    return 50;
                }
            default:
                return 0;
        }

    }





    public static int calculateSumOfValues(int[] diceValues, int valueIAmSearchingFor) {
        return Arrays.stream(diceValues)
                .filter(d -> d == valueIAmSearchingFor)
                .sum();
    }

    private static int calculateOfAKind(int[] diceValues, int numSame, int amountOfPairsImLookingFor) {
        String dicevals = "[ ";
        for (int i : diceValues) {
            dicevals += "" + i +" ";
        }
        dicevals += "]";
        System.out.println("Dice Values: " + dicevals + ", number of a kind: " + numSame + ", Looking for: " + amountOfPairsImLookingFor);
        Map<Integer, Integer> recordOfAppearance = appearanceRecordOfEachNumber(diceValues);
        Map<Integer, Integer> recordOfAppearanceButSorted = sortingTheAppearanceMap(recordOfAppearance);
        Map<Integer, Integer> stored = new HashMap<>();
        int tempSum = 0;
        int sum = 0;
        int pairsFound = 0;
        for (int i = 1; i < 7; i++) {
            int key = i;
            if (recordOfAppearanceButSorted.get(key) != null) {
                int value = recordOfAppearanceButSorted.get(key);
                if (value >= numSame) {
                    tempSum = key * numSame;
                    pairsFound++;
                    System.out.println("Adding Key: " + key + ", Value: " + value);
                    stored.put(key, tempSum);
                }
            }
        }
        int added = 0;
        if (pairsFound >= amountOfPairsImLookingFor) {
            for (int i = 6; i > 0; i--) {
                if (stored.get(i) != null) {
                    System.out.println("Adding " + stored.get(i) + " to sum.");
                    sum += stored.get(i);
                    added++;
                }
                if (added == amountOfPairsImLookingFor) {
                    System.out.println("Breaking...");
                    break;
                }

            }
        }
        System.out.println("Sum: " +sum);
        return sum;
    }


    /** Calculate The Ammount Of Times A Single Value Appears In My 5 Dices **/
    public static Map<Integer, Integer> appearanceRecordOfEachNumber(int[] diceValues) {
        return Arrays.stream(diceValues)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
    }

    /**Receives the appearance record and output a copy of the map but now sorted by the keys
    * remeber, the keys are the numbers in the dices and the values are the calculated sum of
    * how many times they appeared
     * */
    public static Map<Integer, Integer> sortingTheAppearanceMap(Map<Integer, Integer> appearanceMap)
    {
        Map<Integer, Integer> sortedValueCounts = new LinkedHashMap<>();
        appearanceMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByKey().reversed())
                .forEachOrdered(e -> sortedValueCounts.put(e.getKey(), e.getValue()));
        return sortedValueCounts;
    }

    public int calculatingStraight(int[] diceValues, int[] arrayThatIAmComparingTo) {
        System.out.print("Input:");
        for (int i : diceValues) {
            System.out.print(" "+i);
        }
        System.out.println(".");
        System.out.print("Goal: ");
        for (int i : arrayThatIAmComparingTo) {
            System.out.print(" "+i);
        }
        System.out.println(".");
        boolean isStraight = true;
        ArrayList<Integer> DiceVals = new ArrayList<>();
        for (int i : diceValues) {
            DiceVals.add(i);
        }
        for (int i : arrayThatIAmComparingTo) {
            if (!DiceVals.contains(i)) {
                isStraight = false;
            }
        }
        System.out.println("Result: " + isStraight);
        System.out.println("**********************");
        if (isStraight) {
            return Arrays.stream(diceValues).sum();
        } else {
            return 0;
        }
    }




}
