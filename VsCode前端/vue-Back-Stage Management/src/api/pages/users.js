import request from '@/utils/request'
//查询所有用户带分页
export default {
    list(pageNum, pageSize, usersQuery) {
        return request({
            // 两种写法（1）'/classify/api/list/'+pageNum+'/'+pageSize,
            // 第二种写法
            url: `/users/api/list/${pageNum}/${pageSize}`,
            method: 'post',
            data: usersQuery
        })
    },
    //删除操作
    getDelById(id) {
        return request({
            url: '/users/api/delete/' + id,
            method: 'delete'
        })
    },
    //添加用户
    save(users) {
        return request({
            url: '/users/api/save',
            method: 'post',
            data: users
        })
    },
    //修改数据
    update(users) {
        return request({
            url: '/users/api/update',
            method: 'put',
            data: users
        })
    },
}