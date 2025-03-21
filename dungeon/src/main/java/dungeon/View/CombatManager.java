package dungeon.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

import dungeon.Atom;
import dungeon.DungeonCharacter;
import dungeon.HealthPotion;
import dungeon.Hero;

public class CombatManager {
    private static final Random rand = new Random();
    private static final int HEALTH_POTION_HEAL_AMOUNT = 30; // Health restored by potion

    public static void battle(JFrame parentFrame, DungeonCharacter hero, DungeonCharacter monster) {
        JDialog battleDialog = new JDialog(parentFrame, "Battle", true);
        battleDialog.setLayout(new BorderLayout());
        battleDialog.setSize(500, 400);

        JPanel messagePanel = new JPanel();
        JTextArea battleMessage = new JTextArea(10, 30);
        battleMessage.setEditable(false);
        battleMessage.setText("Battle started between " + hero.getName() + " and " + monster.getName() + "\n");
        messagePanel.add(new JScrollPane(battleMessage));

        JPanel healthPanel = new JPanel();
        healthPanel.setLayout(new BorderLayout());

        JLabel heroLabel = new JLabel("Hero HP: ");
        JLabel monsterLabel = new JLabel("Monster HP: ");
        heroLabel.setPreferredSize(new Dimension(100, 25));
        monsterLabel.setPreferredSize(new Dimension(100, 25));

        JProgressBar heroHealthBar = createHealthBar(hero.getHealth(), hero.getMaxHealth());
        JProgressBar monsterHealthBar = createHealthBar(monster.getHealth(), monster.getMaxHealth());

        JPanel heroPanel = new JPanel();
        heroPanel.add(heroLabel);
        heroPanel.add(heroHealthBar);

        JPanel monsterPanel = new JPanel();
        monsterPanel.add(monsterLabel);
        monsterPanel.add(monsterHealthBar);

        healthPanel.add(heroPanel, BorderLayout.NORTH);
        healthPanel.add(monsterPanel, BorderLayout.SOUTH);

        JPanel actionPanel = new JPanel();
        JButton attackButton = new JButton("Regular Attack");
        JButton specialButton = new JButton("Special Attack");
        JButton healthPotionButton = new JButton("Use Health Potion"); // New button

        attackButton.addActionListener(e -> {
            double heroAttackDamage = hero.attack();
            String msg = hero.getName() + " attacks and deals " + Math.round(heroAttackDamage) + " damage to " + monster.getName();
            int numSwords = 0;
            if(hero instanceof Hero hro) {
                numSwords = hro.getNumSwords();
            }
            String numSwordsStr = "";
            if(numSwords > 0) {
                numSwordsStr = " with their " + numSwords + " swords!";
            }
            String finMsg = msg + numSwordsStr + "\n";
            battleMessage.append(finMsg);

            monster.setHealth(monster.getHealth() - heroAttackDamage);
            updateHealthBar(monsterHealthBar, monster.getHealth(), monster.getMaxHealth());

            if (monster.getHealth() <= 0) {
                battleMessage.append(monster.getName() + " has been defeated!\n");
                endBattle(battleDialog, hero, monster);
            } else {
                monsterTurn(hero, monster, battleMessage, heroHealthBar, battleDialog);
            }
        });

        specialButton.addActionListener(e -> {
            battleMessage.append(hero.getName() + " uses special skill!\n");
            if(hero instanceof Hero hro) {
                ArrayList<String> messages = hro.useSpecialSkill(monster);
                for(String str : messages) {
                    battleMessage.append(str + "\n");
                }
            }
            updateHealthBar(heroHealthBar, hero.getHealth(), hero.getMaxHealth());
            updateHealthBar(monsterHealthBar, monster.getHealth(), monster.getMaxHealth());

            if (monster.getHealth() <= 0) {
                battleMessage.append(monster.getName() + " has been defeated!\n");
                endBattle(battleDialog, hero, monster);
            } else {
                monsterTurn(hero, monster, battleMessage, heroHealthBar, battleDialog);
            }
        });

        // Health Potion Button ActionListener
        healthPotionButton.addActionListener(e -> {
            HealthPotion potion = null;
            for(Atom content : hero.getRecursiveContents()) {
                if(content instanceof HealthPotion pot) {
                    potion = pot;
                    break;
                }
            }
            if(potion == null) {
                battleMessage.append("You don't have any potions!");
                return;
            }
            if (hero.getHealth() <= 0) {
                battleMessage.append("You cannot use a potion. " + hero.getName() + " has been defeated!\n");
                return;
            }

            double oldHealth = hero.getHealth();
            potion.use(hero);
            potion = null;

            double newHealth = hero.getHealth();
            double healedAmount = newHealth - oldHealth;

            battleMessage.append(hero.getName() + " used a Health Potion and restored " + (int) healedAmount + " HP!\n");
            updateHealthBar(heroHealthBar, hero.getHealth(), hero.getMaxHealth());

            // Monster's turn after using a potion
            monsterTurn(hero, monster, battleMessage, heroHealthBar, battleDialog);
        });

        actionPanel.add(attackButton);
        actionPanel.add(specialButton);
        actionPanel.add(healthPotionButton);

        battleDialog.add(messagePanel, BorderLayout.CENTER);
        battleDialog.add(healthPanel, BorderLayout.NORTH);
        battleDialog.add(actionPanel, BorderLayout.SOUTH);

        // Cheat Code: Press F1 to Auto-Win
        battleDialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F1) { // If F1 is pressed
                    battleMessage.append("\n*** CHEAT ACTIVATED! ***\n");
                    battleMessage.append(hero.getName() + " instantly defeats " + monster.getName() + "!\n");

                    monster.setHealth(0);
                    updateHealthBar(monsterHealthBar, monster.getHealth(), monster.getMaxHealth());
                    endBattle(battleDialog, hero, monster);
                }
            }
        });


        battleDialog.setFocusable(true);
        battleDialog.requestFocusInWindow(); // Ensure key events are captured
        battleDialog.setLocationRelativeTo(parentFrame);
        battleDialog.setVisible(true);
    }

    private static JProgressBar createHealthBar(double currentHealth, double maxHealth) {
        JProgressBar healthBar = new JProgressBar(0, (int) maxHealth);
        healthBar.setValue((int) currentHealth);
        healthBar.setString((int) currentHealth + " / " + (int) maxHealth);
        healthBar.setStringPainted(true);
        healthBar.setPreferredSize(new Dimension(200, 25));
        return healthBar;
    }

    private static void updateHealthBar(JProgressBar healthBar, double currentHealth, double maxHealth) {
        healthBar.setValue((int) currentHealth);
        healthBar.setString((int) currentHealth + " / " + (int) maxHealth);
    }

    private static void monsterTurn(DungeonCharacter hero, DungeonCharacter monster, JTextArea battleMessage, JProgressBar heroHealthBar, JDialog battleDialog) {
        if (monster.getHealth() > 0) {
            double monsterAttackDamage = monster.attack();
            hero.setHealth(hero.getHealth() - monsterAttackDamage);
            updateHealthBar(heroHealthBar, hero.getHealth(), hero.getMaxHealth());
            battleMessage.append(monster.getName() + " attacks and deals " + monsterAttackDamage + " damage to " + hero.getName() + "\n");

            if (hero.getHealth() <= 0) {
                battleMessage.append(hero.getName() + " has been defeated!\n");
                endBattle(battleDialog, hero, monster);
            }
        }
    }

    private static void endBattle(final JDialog battleDialog, final DungeonCharacter hero, final DungeonCharacter theMonster) {
        if (hero.getHealth() <= 0) {
            JOptionPane.showMessageDialog(battleDialog, "Game Over! " + hero.getName() + " has been defeated.", "Game Over", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(battleDialog, "The battle is over!");
            battleDialog.dispose();
            theMonster.die();
        }
    }
}
