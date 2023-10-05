package plugin.ssumple;

import java.util.Arrays;
import java.util.List;
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
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    /**
     * DAY7javadoc
     *
     * javadoc自分でもできる
     *
     * メソッド作ったあと
     *
     * "/**
     *
     * DAY8フレームワーク
     *
     * フレームワーク
     *
     * ウェブアプリ作成時のフレームワークと今との違い
     *
     * javaウエブアプリ作るときによく使われるフレームワーク
     * spring　makes
     *
     *
     * 自作のはやめたほうが・・・。
     * 自作の場合（オレオレフレームワークと呼ばれることある）
     * 　自分の技術をあげていくべき・価値を
     * 　自社での価値あったとしても、社会に出た時つくりが全く違うことも
     * 　最悪全く役がたたないばあいも。中身よりも独自につくっていくことが問題かも・・・。
     * 　昔貧弱だったから、自作がいくつかある・・・。
     * 　今残っているフレームワークはそれの残ったものともいえるが・・・。
     * 　作るメリットが今ない。
     * 　独自性がある場合は　
     *
     *
     * フレームワークとは
     * 骨組み
     * 何のための骨組みなのか？
     * 　ゲーム、アプリ・・・
     *
     * やりたいことできないのがいちばんいや。
     * 骨ぐみなので、全てかえないといけない。
     * 　なんでもできるものが無難。
     * springはそれができる。　痒い所に手が届く
     *
     * spigotプラグインのための骨組み
     * springウエブアプリのための骨組み
     * やることは同じ規模が違うだけで
     *
     *
     * フレームワーク選び方
     *
     * 好きなものでいい。
     * 悩んだときは人気か
     * 情報量が多い
     *
     * 公式サイト
     * 解説を見てみる
     *
     * springboot今はやっている　お手軽。すぐできる
     * springフレームワーク一般　最初はちょっと難しい
     * bootでもspringの機能使える。
     *
     * gettingstarted　チュートリアル
     */




    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("setLevel").setExecutor(new SetLevelCommand(this));
        getCommand("allSetLevel").setExecutor(new AllSetLevelCommand());
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
