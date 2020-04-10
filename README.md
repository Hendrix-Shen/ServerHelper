# Server Helper

使用 Fabric 模组还原 [MCD](https://github.com/Fallen-Breath/MCDReforged) [部分插件](https://github.com/MCDReforged-Plugins/PluginCatalogue) 功能

## 功能

向其他玩家共享自己的坐标并高亮

统计信息计分板助手

部分指令支持聊天事件触发(触发文本完全可自定义)

例如

`!!ci 立即清理掉落物品`(仅OP和控制台会回显)

`!!here 共享自己的坐标`

`!!op 获取op权限`(若担心滥用问题可以在配置文件中禁用)


`!!restart 重启服务器`(实际上执行的是关服指令, 自动重启依赖于启动脚本)

支持大部分语言文本(其中的大部分都支持Json显示)

### 以下是以还原 [MCD](https://github.com/Fallen-Breath/MCDReforged) [部分插件](https://github.com/MCDReforged-Plugins/PluginCatalogue) 的列表
| 名称                                                 | 维护者                                            | 功能                                     |
| ---------------------------------------------------- | ------------------------------------------------- | ---------------------------------------- |
| [Here](https://github.com/TISUnion/Here)             | [Fallen_Breath](https://github.com/Fallen-Breath) | 显示坐标并高亮玩家                       |
| [SimpleOP](https://github.com/MCDReforged-Plugins/SimpleOP) | [Fallen_Breath](https://github.com/Fallen-Breath) | 使用 `!!op` 来获得 op，`!!restart` 来重启服务器 |
| [StatsHelper](https://github.com/TISUnion/StatsHelper)       | [Fallen_Breath](https://github.com/Fallen-Breath) | 统计信息查询以及计分板创建   |

## 指令

`/core backup <存档>` - 创建一个存档的备份(目前暂时不支持回档操作)

`/core help [页码]` - 显示指令帮助

`/core here [玩家]` - 共享玩家坐标并高亮玩家(若启用高亮) 注意仅 OP 玩家可以高亮其他玩家

`/core reload <all/message/config>` - 重新载入配置文件 仅 OP 玩家

`/core stats check <玩家/UUID> <统计类别> <统计内容> [是UUID] [仅自己可见]` - 查看玩家统计信息

`/core stats rank <统计类别> <统计内容> [仅自己可见]` - 查看统计信息排行

`/core stats refresh` - 刷新UUID缓存 仅 OP 玩家

`/core stats scoreboard <hide/show>` - 打开/关闭计分板的展示

`/core stats scoreboard set <统计类别> <统计内容>` - 设置统计信息计分板

## 常见问题

1. 为什么代码这么糟糕?

呃么么么呃么么么，你管这么多干什么能用不就完了吗？

2. 命令的权限问题？

除标注 仅 OP 玩家 的命令玩家都可以使用

3. 关于UUID缓存刷新的问题，能不能像 [StatsHelper](https://github.com/TISUnion/StatsHelper) 一样在玩家加入时刷新？

可以的, Mixin中预留了玩家进入游戏事件的方法, 可以在那里调用

~~*4. 在后台执行命令不但显示不了信息还会出现 An unexpected error occurred trying to execute that command?*~~

~~*这个不用太担心, 一般是没有错误滴, 只是向后台发送消息不能像向普通玩家发消息一样, 消息没能成功发送.*~~

~~*以后可能会修复.*~~

(最新版已修复)

5. 版本兼容性?

仅在1.14.4的服务端测试过, 其他版本可以试一试. 1.16的快照应该是不行, 有些方法变更了名称.
