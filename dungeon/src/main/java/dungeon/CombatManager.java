package dungeon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CombatManager {
    private static final Random rand = new Random();

    // This method is invoked to start a battle
    public static void battle(JFrame parentFrame, Hero hero, Monster monster) {
        // Create the battle dialog
        JDialog battleDialog = new JDialog(parentFrame, "Battle", true);
        battleDialog.setLayout(new BorderLayout());
        battleDialog.setSize(500, 400);  // Increased size of the dialog

        // Create the battle message panel
        JPanel messagePanel = new JPanel();

        // JTextArea for battle messages
        JTextArea battleMessage = new JTextArea(10, 30);
        battleMessage.setEditable(false);
        battleMessage.setText("Battle started between " + hero.getName() + " and " + monster.getName() + "\n");
        messagePanel.add(new JScrollPane(battleMessage));

        // Create health bars for hero and monster
        JPanel healthPanel = new JPanel();
        JProgressBar heroHealthBar = createHealthBar(hero.getHealth(), hero.getMaxHealth());
        JProgressBar monsterHealthBar = createHealthBar(monster.getHealth(), monster.getMaxHealth());
        healthPanel.add(heroHealthBar);
        healthPanel.add(monsterHealthBar);

        // Create the action buttons panel
        JPanel actionPanel = new JPanel();
        JButton attackButton = new JButton("Regular Attack");
        JButton specialButton = new JButton("Special Attack");

        // Regular Attack Button ActionListener
        attackButton.addActionListener(e -> {
            double heroAttackDamage = hero.attack();
            battleMessage.append(hero.getName() + " attacks and deals " + heroAttackDamage + " damage to " + monster.getName() + "\n");

            monster.setHealth(monster.getHealth() - heroAttackDamage);
            monsterHealthBar.setValue((int) monster.getHealth());

            // Check if monster is defeated
            if (monster.getHealth() <= 0) {
                battleMessage.append(monster.getName() + " has been defeated!\n");
                endBattle(battleDialog);
            } else {
                // Monster attacks back
                double monsterAttackDamage = monster.attack();
                hero.setHealth(hero.getHealth() - monsterAttackDamage);
                heroHealthBar.setValue((int) hero.getHealth());
                battleMessage.append(monster.getName() + " attacks and deals " + monsterAttackDamage + " damage to " + hero.getName() + "\n");
                battleMessage.append(hero.getName() + " Health: " + hero.getHealth() + "\n");

                // Check if hero is defeated
                if (hero.getHealth() <= 0) {
                    battleMessage.append(hero.getName() + " has been defeated!\n");
                    endBattle(battleDialog);
                }
            }
        });

        // Special Attack Button ActionListener
        specialButton.addActionListener(e -> {
            double heroAttackDamage = 75 + Math.random() * 100;
            heroAttackDamage = Math.ceil(heroAttackDamage);
            monster.setHealth(monster.getHealth() - heroAttackDamage);
            monsterHealthBar.setValue((int) monster.getHealth());
            battleMessage.append(hero.getName() + " uses special attack and deals " + heroAttackDamage + " damage to " + monster.getName() + "\n");

            // Check if monster is defeated
            if (monster.getHealth() <= 0) {
                battleMessage.append(monster.getName() + " has been defeated!\n");
                endBattle(battleDialog);
            } else {
                // Monster attacks back
                double monsterAttackDamage = monster.attack();
                hero.setHealth(hero.getHealth() - monsterAttackDamage);
                heroHealthBar.setValue((int) hero.getHealth());
                battleMessage.append(monster.getName() + " attacks and deals " + monsterAttackDamage + " damage to " + hero.getName() + "\n");
                battleMessage.append(hero.getName() + " Health: " + hero.getHealth() + "\n");

                // Check if hero is defeated
                if (hero.getHealth() <= 0) {
                    battleMessage.append(hero.getName() + " has been defeated!\n");
                    endBattle(battleDialog);
                }
            }
        });

        actionPanel.add(attackButton);
        actionPanel.add(specialButton);

        battleDialog.add(messagePanel, BorderLayout.CENTER);
        battleDialog.add(healthPanel, BorderLayout.NORTH);
        battleDialog.add(actionPanel, BorderLayout.SOUTH);

        // Show the battle dialog
        battleDialog.setLocationRelativeTo(parentFrame);
        battleDialog.setVisible(true);
    }

    // Create and configure health progress bar
    private static JProgressBar createHealthBar(double currentHealth, double maxHealth) {
        int healthPercentage = (int) ((currentHealth / maxHealth) * 100);
        JProgressBar healthBar = new JProgressBar(0, 100);
        healthBar.setValue(healthPercentage);
        healthBar.setStringPainted(true);
        healthBar.setPreferredSize(new Dimension(200, 25));
        return healthBar;
    }

    // Method to close the battle dialog once a character has been defeated
    private static void endBattle(JDialog battleDialog) {
        JOptionPane.showMessageDialog(battleDialog, "The battle is over!");
        battleDialog.dispose();  // Close the dialog
    }
}
