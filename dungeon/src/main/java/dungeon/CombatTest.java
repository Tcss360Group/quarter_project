package dungeon;

import java.util.Random;
import java.util.Scanner;

public class CombatTest {

    public static void main(String[] args) {
        testCombat();
    }

    public static void testCombat() {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        // Randomly select a room number between 1 and 25
        int roomNumber = rand.nextInt(25) + 1;
        Atom location = new Room(new int[]{0, 0, 0}, "Room " + roomNumber);

        // Create Warrior and a random Monster
        Warrior warrior = new Warrior(location);
        Monster enemy = MonsterFactory.createRandomMonster(location);

        // Print initial status
        System.out.println("Entering " + ((Room) location).getName());
        System.out.println(warrior.getName() + " Health: " + warrior.getHealth());

        // 50% chance for a battle to occur
        boolean battleOccurs = rand.nextBoolean();

        if (battleOccurs) {
            System.out.println("A battle has started against a " + enemy.getName() + "!");
            System.out.println(enemy.getName() + " Health: " + enemy.getHealth());

            // Combat loop
            while (warrior.getHealth() > 0 && enemy.getHealth() > 0) {
                System.out.println("\nChoose your action:");
                System.out.println("1. Regular Attack");
                System.out.println("2. Special Attack (Crushing Blow)");
                System.out.print("Enter choice: ");

                int choice = scanner.nextInt();
                double warriorAttackDamage = 0;

                if (choice == 1) {
                    warriorAttackDamage = warrior.attack();
                    System.out.println(warrior.getName() + " attacks and deals " + warriorAttackDamage + " damage to " + enemy.getName());
                } else if (choice == 2) {
                    warrior.useSpecialSkill(enemy);
                } else {
                    System.out.println("Invalid choice! You lost your turn.");
                }

                // Apply damage if it was a valid attack
                if (warriorAttackDamage > 0) {
                    enemy.setHealth(enemy.getHealth() - warriorAttackDamage);
                }

                System.out.println(enemy.getName() + " Health: " + enemy.getHealth());

                if (enemy.getHealth() <= 0) {
                    System.out.println(enemy.getName() + " has been defeated!");
                    break;
                }

                // Enemy attacks
                double enemyAttackDamage = enemy.attack();
                warrior.setHealth(warrior.getHealth() - enemyAttackDamage);
                System.out.println(enemy.getName() + " attacks and deals " + enemyAttackDamage + " damage to " + warrior.getName());
                System.out.println(warrior.getName() + " Health: " + warrior.getHealth());

                if (warrior.getHealth() <= 0) {
                    System.out.println(warrior.getName() + " has been defeated!");
                    break;
                }
            }

            // Print final status
            System.out.println("\nFinal Status:");
            System.out.println(warrior.getName() + " Health: " + warrior.getHealth());
            System.out.println(enemy.getName() + " Health: " + enemy.getHealth());
        } else {
            System.out.println("No battle occurred in this room.");
        }

        scanner.close();
    }
}
