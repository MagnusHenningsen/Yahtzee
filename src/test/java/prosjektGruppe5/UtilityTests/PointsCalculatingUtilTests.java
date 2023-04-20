package prosjektGruppe5.UtilityTests;

import org.junit.Test;
import prosjektGruppe5.Utilities.PointsCalculatingUtil;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PointsCalculatingUtilTests {
    private PointsCalculatingUtil pu = new PointsCalculatingUtil();
    @Test
    public void TestStraight() {
        int[] notStraight = {1,2,2,3,4};
        int[] lowerStraight = {1,2,3,4,5};
        int[] higherStraight = {2,3,4,5,6};
        int[] straight1 = {2,3,1,5,4};
        int[] straight2 = {5,1,3,2,4};
        int[] straight3 = {2,3,6,5,4};
        int[] straight4 = {5,6,3,2,4};
        assertEquals(pu.calculatingStraight(notStraight, higherStraight), 0);
        assertEquals(pu.calculatingStraight(notStraight, lowerStraight), 0);

        assertEquals(pu.calculatingStraight(straight1, higherStraight), 0);
        assertEquals(pu.calculatingStraight(straight1, lowerStraight), 15);

        assertEquals(pu.calculatingStraight(straight2, higherStraight), 0);
        assertEquals(pu.calculatingStraight(straight2, lowerStraight), 15);

        assertEquals(pu.calculatingStraight(straight3, higherStraight), 20);
        assertEquals(pu.calculatingStraight(straight3, lowerStraight), 0);

        assertEquals(pu.calculatingStraight(straight4, higherStraight), 20);
        assertEquals(pu.calculatingStraight(straight4, lowerStraight), 0);

    }
    @Test
    public void TestGetRightAmountOfPoints() {
        // int[] diceValues,Integer currentRound
        int CurrentRound = 0;
        int[] values = new int[5];
        int result = 0;
        for (; CurrentRound < 15; CurrentRound++) {
            switch (CurrentRound) {
                case 0:
                    values = new int[]{1, 1, 1, 1, 1};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(5, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0, result);
                    break;
                case 1:
                    values = new int[]{2, 2, 2, 2, 2};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(10, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(2, result);
                    break;
                case 2:
                    values = new int[]{3, 3, 3, 3, 3};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(15, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(3, result);
                    break;
                case 3:
                    values = new int[]{4, 4, 4, 4, 4};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(20, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(4, result);
                    break;
                case 4:
                    values = new int[]{5, 5, 5, 5, 5};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(25, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(5, result);
                    break;
                case 5:
                    values = new int[]{6, 6, 6, 6, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(30, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(6, result);
                    break;
                case 6:
                    values = new int[]{6, 6, 2, 1, 1};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(12, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0, result);
                    break;
                case 7:
                    values = new int[]{6, 6, 2, 2, 1};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(16, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0, result);
                    break;
                case 8:
                    values = new int[]{6, 6, 6, 6, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(18, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0,result);
                    break;
                case 9:
                    values = new int[]{6, 6, 6, 6, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals( 24, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0, result);
                    break;
                case 10:
                    values = new int[]{1, 2, 3, 4, 5};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(15, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0, result);
                    break;
                case 11:
                    values = new int[]{1, 2, 3, 4, 5};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(20, result);
                    break;
                case 12:
                    values = new int[]{2, 2, 2, 5, 5};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(16, result);
                    values = new int[]{2, 3, 4, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0, result);
                    break;
                case 13:
                    values = new int[]{1, 2, 3, 4, 5};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(15, result);
                    values = new int[]{6, 6, 6, 5, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(29, result);
                    break;
                case 14:
                    values = new int[]{1, 2, 3, 4, 5};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(0, result);
                    values = new int[]{1, 1, 1, 1, 1};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(50, result);
                    values = new int[]{3, 3, 3, 3, 3};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(50, result);
                    values = new int[]{6, 6, 6, 6, 6};
                    result = pu.getRightAmountOfPoints(values, CurrentRound);
                    assertEquals(50, result);
                    break;
                default:
                    // fail();
                    break;
            }
        }
    }
}
