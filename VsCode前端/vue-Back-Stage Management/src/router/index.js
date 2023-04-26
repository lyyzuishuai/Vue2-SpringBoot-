import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)


import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },


  /* 配置商品分类路由信息 */
  {
    path: '/classify',
    component: Layout,
    redirect: '/shop',
    name: 'Shop',
    meta: { title: '商品分类管理', icon: 'el-icon-shopping-bag-2' },
    alwaysShow: true,
    children: [
      /* 查询所有分类数据*/
      {
        path: 'list',
        name: 'listClassify',
        component: () => import('@/views/pages/classify/index'),
        meta: { title: '商品分类列表', icon: 'el-icon-present' }

      },
      {
        path: 'goods',
        name: 'Goods',
        component: () => import('@/views/pages/goods/index'),
        meta: { title: '商品列表', icon: 'el-icon-shopping-cart-full' }
      }
    ]
  },

  //用户信息配置
  {
    path: '/users',
    component: Layout,
    redirect: '/users',
    name: 'users',
    meta: { title: '用户管理', icon: 'el-icon-user-solid' },
    alwaysShow: true,
    children: [
      /* 查询所有分类数据*/
      {
        path: 'list',
        name: 'listUsers',
        component: () => import('@/views/pages/users/index'),
        meta: { title: '用户列表', icon: 'el-icon-user' }

      },
      {
        path: 'address',
        name: 'address',
        component: () => import('@/views/pages/address/index'),
        meta: { title: '用户地址列表', icon: 'el-icon-right' }
      }
    ]
  },

  //订单信息配置
  {
    path: '/orders',
    component: Layout,
    redirect: '/orders',
    name: 'orders',
    meta: { title: '订单管理', icon: 'el-icon-user-solid' },
    alwaysShow: true,
    children: [
      /* 查询所有分类数据*/
      {
        path: 'listOrders',
        name: 'listOrders',
        component: () => import('@/views/pages/orders/index'),
        meta: { title: '订单列表', icon: 'el-icon-user' }
      },
      {
        path: 'orderUser',
        name: 'orderUser',
        component: () => import('@/views/pages/orderUser/index'),
        meta: { title: '订单状态', icon: 'el-icon-user' }
      },
    ]
  },
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
