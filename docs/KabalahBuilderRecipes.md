Kabalah Builder 配方自定义说明

目的
-
允许通过 `config/gctcore.cfg` 自定义 `kabalah_builder`（生命树构建器）的配方。Gct Core 提供了 `ConfigHandler` 以解析这些条目并在运行时暴露为数据结构，供其它模组（例如 gct_mobs）在初始化时读取并注册。

配置位置
-
配置文件：`config/gctcore.cfg`

配置键
-
在 `kabalah_builder` 类别下使用键 `kabalah_builder_recipes`，值为字符串数组。每个条目为一条配方。

语法
-
每条配方使用箭头分隔输出与输入：

输出描述 => 输入1, 输入2, ...

输出/输入格式: `modid:resource [count]`（count 可选，默认为 1）

示例条目
-
gctcore:my_item 1 => minecraft:stone 4, minecraft:log 2

含义：构建器消耗 4 个石头和 2 个原木，生成 1 个 `gctcore:my_item`。

使用方法（开发者）
-
Gct Core 在 `preInit` 时读取并解析配置；要在 `gct_mobs`（或其它实现 Kabalah Builder 的模组）中使用这些配方，请在该模组的初始化代码中调用：

```java
List<com.smd.gctcore.config.ConfigHandler.KabalahRecipe> recipes = com.smd.gctcore.config.ConfigHandler.getKabalahRecipes();
// 由目标模组将这些 recipes 转换为它自身的配方结构并注册
```

示例（伪代码）
-
如果 gct_mobs 提供了一个静态注册方法 `KabalahBuilderRegistry.registerRecipe(...)`，可以：

```java
for (ConfigHandler.KabalahRecipe r : recipes) {
    // 转换为目标模组的内部配方类型
    TargetRecipe tr = convert(r);
    KabalahBuilderRegistry.registerRecipe(tr);
}
```

注意事项
-
- Gct Core 只负责解析配置并把结构化数据暴露给调用方；具体如何注册到 `kabalah_builder`（TileEntity / GUI / 配方管理）依赖于目标模组的实现。
- 如果你希望我自动把配方注入 `gct_mobs`，请确认 `gct_mobs` 的配方注册入口（类名 / 方法），或把 `gct_mobs` 的源码放入同一工作区，我可以为你实现反射注入或直接调用。
