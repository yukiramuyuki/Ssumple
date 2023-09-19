package plugin.ssumple;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLevelCommand implements CommandExecutor {
  //      CommandExecutorを使っているだけだから、getconfigできない。
//      →setLevelCommandにMainクラスの機能をつかえるようにする！！
  private Main main;

  public SetLevelCommand(Main main) {
    this.main = main;
  }
//   手順４ Mainクラスの機能を使えるようにするため、private Main main;右クリック→生成→コントラクター
//  ※赤文字　理由：コンストラクタ―を定義して、引数Main
//  →隠されていたものが出される＋引数とると指定
//  newするときMain必要と強制できて、している。
//  Mainの方でsetolevelcommand()引数ない。ため。

//  赤文字出さない方法→コントラクター時選択する。
//  コンストラクタ―複数作成し、引数いるもの、いらないものを作った（どっちでもいいよ）
//  今回はMainからgetconfigなど使いたいから赤文字のほう！必ず使うから


  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player player) {
      if (args.length == 1) {
        player.setLevel(Integer.parseInt(args[0]));
      } else {
        player.sendMessage(main.getConfig().getString("Message"));
      }
//     手順６elseの固定値を 設定ファイルの選択のなかから出す
//      Noooの文字を消す。固定値のため
//      Mainの中のJavaPluginなども使えるようになる
//      コンストラクタ―を使ってクラス、値をひきわたす
//      →Javaのなかでよく使う。他でも
//      config.ymlのMessageが出るはず。

//      タスクの実行、サーバーを開いたら、minecraftで新しいファイルが作成されている。
//      MainのsaveDefaultConfig()の部分

//      minecraftでsetlevelでもじでる
//      config.ymlを変更する→マイクラ変更なし
//      →サーバーにreloadと入力→変更されている。

//      マルチプレイ（複数人で）の時、サーバーを変更できない
//      メッセージの内容などを変更するとき、ファイルの読み書きをする



    }
    return false;
  }
}
