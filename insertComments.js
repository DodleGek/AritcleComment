// 导入MongoDB的Node.js驱动程序
const MongoClient = require('mongodb').MongoClient;

// MongoDB的连接字符串，添加了用户名和密码
const url = 'mongodb://root:123456@127.0.0.1:27017';
// 需要连接的数据库名称，需要替换为实际的数据库名称
const dbName = 'articledb';

// 连接到MongoDB数据库的异步函数
async function connect() {
    // 创建一个新的MongoDB客户端
    const client = new MongoClient(url, { useUnifiedTopology: true });
    // 连接到MongoDB服务器
    await client.connect();
    // 返回连接到的数据库
    return client.db(dbName);
}

// 生成评论数据的函数，参数i用于生成唯一的评论内容和用户信息，parentId用于设置父评论ID
function generateComment(i, parentId) {
    return {
        content: `评价${i}号`, // 评论内容
        publishtime: new Date(), // 发布时间
        userid: `${i}`, // 用户ID
        nickname: `${2333}`, // 用户昵称
        createdatetime: new Date(), // 创建时间
        likenum: 0, // 点赞数
        replynum: 0, // 回复数
        state: 1, // 状态
        parentid: parentId, // 父评论ID
        articleid: `${20231214}` // 文章ID
    };
}

// 将评论插入到数据库的异步函数
async function insertComment(db, comment) {
    // 在'comment'集合中插入一个文档，并等待操作完成
    const result = await db.collection('comment').insertOne(comment);
    // 打印插入的文档的ID
    console.log(`Inserted comment with id: ${result.insertedId}`);
}

// 主函数
async function main() {
    // 连接到数据库
    const db = await connect();
    // 循环生成并插入评论
    for (let i = 0; i < 2; i++) {
        // 生成一个父评论
        const parentComment = generateComment(i, '0');
        await insertComment(db, parentComment);
        // 生成一些子评论
        for (let j = 0; j < 10; j++) {
            const childComment = generateComment(10 * i + j, parentComment.id);
            await insertComment(db, childComment);
        }
    }
}

// 运行主函数，并捕获任何可能出现的错误
main().catch(console.error);