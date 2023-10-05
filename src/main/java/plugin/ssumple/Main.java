package plugin.ssumple;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("setLevel").setExecutor(new SetLevelCommand(this));
        getCommand("allSetLevel").setExecutor(new AllSetLevelCommand());
    }



    //    入った時
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();
        Location pyaerLocation = player.getLocation();
//        今いる位置を取得
        world.spawnEntity(new Location(world,0 0 0 ), EntityType.CHICKEN)
//        花火のときロケーションを指定するか、クラスを指定するかの違い
//        "world.spawnEntity"を右クリックリファクタリング変数の導入entityとれる
//        entity広い。チキンでとれるけど、エラーになる。引数のチキンをプレイヤーに変えてもできる。でもエラーになる。
//       キャストはできる限り避ける
        
    }




    /**
     * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
     *
     * @param e イベント
     */
    private int count;
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
        Player player = e.getPlayer();
        World world = player.getWorld();

        List<Color> colorList = List.of(Color.RED, Color.BLUE, Color.WHITE, Color.PURPLE,
            Color.WHITE);

        if (count % 2 == 0) {
            for (Color color : colorList) {

                // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
                Firework firework = world.spawn(player.getLocation(), Firework.class);

                // 花火オブジェクトが持つメタ情報を取得。
                FireworkMeta fireworkMeta = firework.getFireworkMeta();

                // メタ情報に対して設定を追加したり、値の上書きを行う。
                // 今回は青色で星型の花火を打ち上げる。
                fireworkMeta.addEffect(
                    FireworkEffect.builder()
                        .withColor(color)
                        .with(Type.STAR)
                        .withFlicker()
                        .build());
                fireworkMeta.setPower(1);

                // 追加した情報で再設定する。
                firework.setFireworkMeta(fireworkMeta);
            }

        }
        count++;


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
