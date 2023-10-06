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
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;/
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

  /**
   * 開発経験がないと設計は難しい
   * 作ったことが無い料理はどう作るか
   * 知識がない
   *
   * まず手を動かす
   *
   * filコマンド（しきつめる）
   *
   *
   *
   * マインクラフトプラグイン開発（タイトル）
   *
   * 機能要件・非機能要件・機能設計
   * 
   *
   * 機能要件
   * ログインできること
   * 新規登録できること
   * （ないとだめなもの）
   * ゲームだと、
   * 敵を倒すとてんすうが手に入ること
   * 時間制限を設定できること
   * 時間制限が来たら合計の点数が表示されること
   * 　ゲームのルール。ないと成立しない
   * それがないとだめ。携帯だと電話できないとだめ
   * できてあたりまえ。できないとだめ
   *
   * 非機能要件
   * 携帯だと軽い。必須ではないけど、あると嬉しい。
   * 一番重要
   * 顧客満足度。お客さんが求めているもの。
   * いくらでもだせる。
   * コマンドでゲームを開始できる。
   * コマンドのオプションで敵の強さ、プライヤーの強さ、敵の種類を設定できる。
   * プラグインをどのサーバーでも動かせること。
   * 同時に。
   * 非機能を満たすために機能をある程度考慮していないと。
   *
   * 機能設計
   * 点が入る敵を倒すと。要件定理。何を使うか。
   * 敵を倒すと点数が手に入る
   * 　EntityのSpownの仕組みを使って敵を出現させる。
   * 　Entityが倒れた時のイベントを使って、（スコアとしない。）点数を設定する。
   *
   * スコアを点数を変える。スコア、点数同じ意味でも統一させる。認識が違うこともあるから。
   * 敵をモンスター。敵イコールモンスターの認識ではないかも。
   * 点数とは何かも設計。
   *
   * 簡単なメモでもいい。まとめておいて残しておく
   * きちんとメンテナンスをしておくと、後で見返したときにプラグインの確認などで資料としてのこっているため
   * 思い返しやすい。
   * 抜いたり、足したり自由に。（お客さんとするときは勝手に抜いたりはダメ）
   */


    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("setLevel").setExecutor(new SetLevelCommand(this));
        getCommand("allSetLevel").setExecutor(new AllSetLevelCommand());
        
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();
        Location playaerLocation = player.getLocation();
        world.spawn(new Location(world, playaerLocation.getX()+3,playaerLocation.getY(),playaerLocation.getZ()), Chicken.class);





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
