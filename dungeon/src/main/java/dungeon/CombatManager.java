package dungeon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        battleDialog.setSize(500, 400);

        // Create the battle message panel
        JPanel messagePanel = new JPanel();

        // JTextArea for battle messages
        JTextArea battleMessage = new JTextArea(10, 30);
        battleMessage.setEditable(false);
        battleMessage.setText("Battle started between " + hero.getName() + " and " + monster.getName() + "\n");
        messagePanel.add(new JScrollPane(battleMessage));

        // Create health bars for hero and monster
        JPanel healthPanel = new JPanel();
        healthPanel.setLayout(new BorderLayout());

        // Create and set up labels for hero and monster HP
        JLabel heroLabel = new JLabel("Hero HP: ");
        JLabel monsterLabel = new JLabel("Monster HP: ");
        heroLabel.setPreferredSize(new Dimension(100, 25));
        monsterLabel.setPreferredSize(new Dimension(100, 25));

        // Hero health bar
        JProgressBar heroHealthBar = createHealthBar(hero.getHealth(), hero.getMaxHealth());

        // Monster health bar
        JProgressBar monsterHealthBar = createHealthBar(monster.getHealth(), monster.getMaxHealth());

        // Add labels and health bars to healthPanel
        JPanel heroPanel = new JPanel();
        heroPanel.add(heroLabel);
        heroPanel.add(heroHealthBar);

        JPanel monsterPanel = new JPanel();
        monsterPanel.add(monsterLabel);
        monsterPanel.add(monsterHealthBar);

        // Add the panels to the healthPanel
        healthPanel.add(heroPanel, BorderLayout.NORTH);
        healthPanel.add(monsterPanel, BorderLayout.SOUTH);

        // Create the action buttons panel
        JPanel actionPanel = new JPanel();
        JButton attackButton = new JButton("Regular Attack");
        JButton specialButton = new JButton("Special Attack");

        // Regular Attack Button ActionListener
        attackButton.addActionListener(e -> {
            double heroAttackDamage = hero.attack();
            battleMessage.append(hero.getName() + " attacks and deals " + heroAttackDamage + " damage to " + monster.getName() + "\n");

            monster.setHealth(monster.getHealth() - heroAttackDamage);
            updateHealthBar(monsterHealthBar, monster.getHealth(), monster.getMaxHealth());

            // Check if monster is defeated
            if (monster.getHealth() <= 0) {
                battleMessage.append(monster.getName() + " has been defeated!\n");
                endBattle(battleDialog);
            } else {
                // Monster attacks back
                double monsterAttackDamage = monster.attack();
                hero.setHealth(hero.getHealth() - monsterAttackDamage);
                updateHealthBar(heroHealthBar, hero.getHealth(), hero.getMaxHealth());
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
            battleMessage.append(hero.getName() + " uses special skill!\n");
            
            // Call the hero's unique special skill
            hero.useSpecialSkill();

            // Update monster's health after the skill is used
            updateHealthBar(monsterHealthBar, monster.getHealth(), monster.getMaxHealth());

            // Check if monster is defeated
            if (monster.getHealth() <= 0) {
                battleMessage.append(monster.getName() + " has been defeated!\n");
                endBattle(battleDialog);
            } else {
                // Monster attacks back
                double monsterAttackDamage = monster.attack();
                hero.setHealth(hero.getHealth() - monsterAttackDamage);
                updateHealthBar(heroHealthBar, hero.getHealth(), hero.getMaxHealth());
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
        JProgressBar healthBar = new JProgressBar(0, (int) maxHealth);
        healthBar.setValue((int) currentHealth);
        healthBar.setString((int) currentHealth + " / " + (int) maxHealth); // Show health values
        healthBar.setStringPainted(true);
        healthBar.setPreferredSize(new Dimension(200, 25));
        return healthBar;
    }

    // Method to update health bar values in real-time
    private static void updateHealthBar(JProgressBar healthBar, double currentHealth, double maxHealth) {
        healthBar.setValue((int) currentHealth);
        healthBar.setString((int) currentHealth + " / " + (int) maxHealth); // Update displayed text
    }

    // Method to close the battle dialog once a character has been defeated
    private static void endBattle(JDialog battleDialog) {
        JOptionPane.showMessageDialog(battleDialog, "The battle is over!");
        battleDialog.dispose();  // Close the dialog
    }
}
