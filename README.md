# Build

包含所有项目通用的构建逻辑。

# Structure

- `bom`: 包含较为复杂的大型依赖
- `catalog`: 包含所有依赖的具体坐标
- `repositories`: 包含所有会用到的 repositories

# How to update

1. `git pull` 更新此项目的文件
2. `gradle :deployAll` 将依赖部署到远程仓库
