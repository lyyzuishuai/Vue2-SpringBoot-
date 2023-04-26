import request from '@/utils/request'

export default {
    listGoods(classify) {
        return request({
            url: `/classify/api/goods/list`,
            method: 'get',
            data: classify
        })
    },


    // 查询商品分类
    list(pageNum, pageSize, classifyQuery) {
        return request({
            // 两种写法（1）'/classify/api/list/'+pageNum+'/'+pageSize,
            // 第二种写法
            url: `/classify/api/list/${pageNum}/${pageSize}`,
            method: 'post',
            data: classifyQuery
        })
    },
    // 删除
    getDelById(id) {
        return request({
            url: '/classify/api/delete/' + id,
            method: 'delete'
        })
    },
    // 新增
    save(classify) {
        return request({
            url: '/classify/api/add',
            method: 'post',
            data: classify
        })
    },
    //修改
    update(classify) {
        return request({
            url: '/classify/api/edit',
            method: 'put',
            data: classify
        })
    }
}