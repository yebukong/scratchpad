package pers.mine.scratchpad;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalAccessor;

/**
 */
public class SwitchPatternMatchingTest {

    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> obj.toString();
        };
    }

    static void testFooBarOld(String s) {
        switch (s) {
            case null  -> System.out.println("Oops");
            case "Foo", "Bar" -> System.out.println("Great");
            default           -> System.out.println("Ok");
        }
    }


    // As of Java 21
    static void testStringNew(String response) {
        switch (response) {
            case null -> { }
            case String s
                    when s.equalsIgnoreCase("YES") -> {
                System.out.println("You got it");
            }
            case String s
                    when s.equalsIgnoreCase("NO") -> {
                System.out.println("Shame");
            }
            case String s -> {
                System.out.println("Sorry?");
            }
        }
    }

    static void testStringEnhanced(String response) {
        switch (response) {
            case null -> { }
            case "y", "Y" -> {
                System.out.println("You got it1");
            }
            case "n", "N" -> {
                System.out.println("Shame");
            }
            case String s
                    when s.equalsIgnoreCase("YES") -> {
                System.out.println("You got it");
            }
            case String s
                    when s.equalsIgnoreCase("NO") -> {
                System.out.println("Shame");
            }
            case String s -> {
                System.out.println("Sorry?");
            }
        }
    }

    sealed interface CardClassification permits Suit, Tarot {}
    public enum Suit implements CardClassification { CLUBS, DIAMONDS, HEARTS, SPADES }
    final class Tarot implements CardClassification {}

    // As of Java 21
    static void exhaustiveSwitchWithBetterEnumSupport(TemporalAccessor t) {
        switch (t) {
            case Month.JANUARY,Month.FEBRUARY, Month.MARCH-> {
                System.out.println("It's Month");
            }
            case DayOfWeek.FRIDAY -> {
                System.out.println("It's Month");
            }
            default -> System.out.println("default");
        }
    }

    public static void main(String[] args) {
        testFooBarOld(null);
    }
}
