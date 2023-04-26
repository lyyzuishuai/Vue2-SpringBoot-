import request from '@/utils/request'
export default {
    //查询地址
    list(pageNum, pageSize, addressQuery) {
        return request({
            // 两种写法（1）'/classify/api/list/'+pageNum+'/'+pageSize,
            // 第二种写法
            url: `/address/api/list/${pageNum}/${pageSize}`,
            method: 'post',
            data: addressQuery
        })
    },
    //删除操作
    getDelById(addressId) {
        return request({
            url: '/address/api/delete/' + addressId,
            method: 'delete'
        })
    },
    //添加地址
    save(address) {
        return request({
            url: '/address/api/save',
            method: 'post',
            data: address
        })
    },
    //修改数据
    update(address) {
        return request({
            url: '/address/api/update',
            method: 'put',
            data: address
        })
    },
}