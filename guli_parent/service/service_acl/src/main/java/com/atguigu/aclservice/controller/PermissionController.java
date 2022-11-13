package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.entity.Permission;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 查询所有菜单数据
     * @return
     */
    @ApiOperation(value = "自定义递归查询")
    @GetMapping("/all")
    public R getAllPermission(){
        List<Permission> permissionList = permissionService.getAllPermission();
        return R.ok().data("permissionList",permissionList);
    }

    /**
     * 删除菜单及所有子菜单
     * @param id
     * @return
     */
    @ApiOperation(value = "自定义递归删除")
    @DeleteMapping("/delete/{permissionId}")
    public R deleteById(@PathVariable("permissionId") String id){
        permissionService.deleteById(id);
        return R.ok();
    }

    /**
     * 给角色分配权限
     * @param roleId 角色id
     * @param permissionIds 菜单id
     * @return
     */
    @ApiOperation(value = "自定义给角色分配权限")
    @PostMapping ("/privilege")
    public R setRolePermission(String roleId, String[] permissionIds){
        permissionService.setRolePermission(roleId, permissionIds);
        return R.ok();
    }


    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public R indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return R.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return R.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

