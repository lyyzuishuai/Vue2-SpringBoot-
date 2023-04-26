import request from '@/utils/request'

export default {
    //查询地址
    list(pageNum, pageSize, ordersQuery) {
        return request({
            // 两种写法（1）'/classify/api/list/'+pageNum+'/'+pageSize,
            // 第二种写法
            url: `/orders/api/list/${pageNum}/${pageSize}`,
            method: 'post',
            data: ordersQuery
        })
    },
    save(orders) {
        return request({
            url: '/orders/api/save',
            method: 'post',
            data: orders
        })
    },

    update(Orders) {
        return request({
            url: '/orders/api/edit',
            method: 'put',
            data: Orders
        })
    },

    getByid(id) {
        return request({
            url: "/orders/api/edit/select/" + id,
            method: "put"
        })
    }
}