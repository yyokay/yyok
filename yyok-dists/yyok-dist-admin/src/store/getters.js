const getters = {
  deployUploadApi: state => state.api.deployUploadApi,
  databaseUploadApi: state => state.api.databaseUploadApi,
  size: state => state.app.size,
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  //集中帐号（account）管理
  account: state => state.account,
  user: state => state.account.user,
  //token
  token: state => state.account.token,
  //角色管理
  roles: state => state.account.roles,
  //授权:权限:集中权限(authorization)管理
  authority: state => state.account.authority,
  //身份验证; 认证；鉴定;  集中认证(authentication)管理
  authentication: state => state.account.authentication,
  //访问控制列表
  acl: state => state.account.acl,
  //属性的权限管理模型
  abac: state => state.account.abac,
  //资源
  loadResources: state => state.account.loadResources,

  permission_routers: state => state.permission.routers,

  addRouters: state => state.permission.addRouters,

  socketApi: state => state.api.socketApi,
  imagesUploadApi: state => state.api.imagesUploadApi,
  baseApi: state => state.api.baseApi,
  fileUploadApi: state => state.api.fileUploadApi,
  uploadApi: state => state.api.uploadApi,
  updateAvatarApi: state => state.api.updateAvatarApi,
  qiNiuUploadApi: state => state.api.qiNiuUploadApi,
  sqlApi: state => state.api.sqlApi,
  swaggerApi: state => state.api.swaggerApi
}
export default getters
