import request from '@/utils/request'

export default {
    // 查询商品
    list(pageNum, pageSize, goodsQuery) {
        return request({
            // 两种写法（1）'/goods/api/list/'+pageNum+'/'+pageSize,
            // 第二种写法
            url: `/goods/api/list/${pageNum}/${pageSize}`,
            method: 'post',
            data: goodsQuery
        })
    },

    // 删除功能
    getDelById(id) {
        return request({
            url: '/goods/api/delete/' + id,
            method: 'delete'
        })
    },

    // 添加数据
    addGoods(goods) {
        return request({
            url: '/goods/api/add',
            method: 'post',
            data: goods
        })
    },

    // 修改数据
    update(goods) {
        return request({
            url: '/goods/api/edit',
            method: 'put',
            data: goods
        })
    }
}
