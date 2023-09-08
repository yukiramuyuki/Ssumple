package plugin.ssumple;

import java.util.Arrays;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;A
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.ssumple.LevelUpCommand;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("levelup").setExecutor(new LevelUpCommand());
    }

    /**
     * �v���C���[���X�j�[�N���J�n/�I������ۂɋN�������C�x���g�n���h���B
     *
     * @param e �C�x���g
     */
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        // �C�x���g�������̃v���C���[�⃏�[���h�Ȃǂ̏���ϐ��Ɏ��B
        Player player = e.getPlayer();
        World world = player.getWorld();

        // �ԉ΃I�u�W�F�N�g���v���C���[�̃��P�[�V�����n�_�ɑ΂��ďo��������B
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // �ԉ΃I�u�W�F�N�g�������^�����擾�B
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // ���^���ɑ΂��Đݒ��ǉ�������A�l�̏㏑�����s���B
        // ����͐F�Ő��^�̉ԉ΂�ł��グ��B
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(Color.BLUE)
                .with(Type.STAR)
                .withFlicker()
                .build());
        fireworkMeta.setPower(0);

        // �ǉ��������ōĐݒ肷��B
        firework.setFireworkMeta(fireworkMeta);
    }
}

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent e) {
        Player player = e.getPlayer();
        ItemStack[] itemStacks = player.getInventory().getContents();
        Arrays.stream(itemStacks).filter(
                item -> !Objects.isNull(item) && item.getMaxStackSize() == 64 && item.getAmount() < 30)
            .forEach(item -> item.setAmount(64));
        player.getInventory().setContents(itemStacks);
    }
}
