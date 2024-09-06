package com.bjj.detect.controller;

import com.bjj.detect.entity.DetectRecord;
import com.bjj.detect.entity.VO.DetectRecordVO;
import com.bjj.detect.query.DetectRecordQuery;
import com.bjj.detect.service.DetectRecordService;
import com.syzx.framework.controller.ApiResult;
import com.syzx.framework.controller.ApiResultCode;
import com.syzx.framework.query.QueryResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DetectRecord控制器 <br/>
 * <p>
 * CreateTime 2024/09/03 22:48
 *
 * @version 1.0.0
 * @author 代码生成器
 */
@RequestMapping("/api/detectRecords")
@RestController
public class DetectRecordController {

    //<editor-fold desc="字段区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    @Autowired
    private DetectRecordService detectRecordService;

    //</editor-fold>

    //<editor-fold desc="函数区，此为代码自动生成区，为防止您的代码丢失，请勿在此区域内添加手动代码">

    /**
     * 添加单个实体.
     * @param detectRecord 需要添加的实体
     * @return 服务器统一api回复
     */
    @PostMapping
    public ApiResult<DetectRecord> insert(@RequestBody DetectRecord detectRecord) {
        detectRecordService.insert(detectRecord);
        return new ApiResult<>(ApiResultCode.Success, "添加成功", detectRecord);
    }

    /**
     * 修改单个实体.
     * @param detectRecord 需要修改的实体
     * @return 服务器统一api回复
     */
    @PutMapping
    public ApiResult<DetectRecord> update(@RequestBody DetectRecord detectRecord) {
        detectRecordService.update(detectRecord);
        return new ApiResult<>(ApiResultCode.Success, "修改成功", detectRecord);
    }

    /**
     * 删除单个实体.
     * @param id 需要删除的实体id
     * @return 服务器统一api回复
     */
    @DeleteMapping(value = "/{id}")
    public ApiResult<DetectRecord> delete(@PathVariable long id) {
        detectRecordService.deleteById(id);
        return new ApiResult<>(ApiResultCode.Success, "删除成功", null);
    }

    /**
     * 获取指定ID的实体.
     * @param id 需要获取的实体Id
     * @return 服务器统一api回复
     */
    @GetMapping(value = "/{id}")
    public ApiResult<DetectRecord> getById(@PathVariable long id) {
        DetectRecord detectRecord = detectRecordService.getById(id);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", detectRecord);
    }

    /**
     * 获取所有实体集合.
     * @return 服务器统一api回复
     */
    @GetMapping
    public ApiResult<List<DetectRecord>> get() {
        List<DetectRecord> list = detectRecordService.get();
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 获取符合查询条件的实体集合.
     * @param detectRecordQuery 查询条件
     * @return 服务器统一api回复
     */
    @PostMapping(value = "/noPage")
    public ApiResult<List<DetectRecord>> getByQuery(@RequestBody DetectRecordQuery detectRecordQuery) {
        List<DetectRecord> list = detectRecordService.getByQuery(detectRecordQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

    /**
     * 分页获取符合查询条件的实体集合.
     * @param detectRecordQuery 查询条件
     * @return 服务器统一api回复
     */
//    @PostMapping(value = "/page")
//    public ApiResult<QueryResult<DetectRecord>> pageByQuery(@RequestBody DetectRecordQuery detectRecordQuery) {
//        QueryResult<DetectRecord> list = detectRecordService.pageByQuery(detectRecordQuery);
//        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
//    }

    //</editor-fold>

    @PostMapping(value = "/page")
    public ApiResult<QueryResult<DetectRecordVO>> pageByQuery(@RequestBody DetectRecordQuery detectRecordQuery) {
        QueryResult<DetectRecordVO> list = detectRecordService.pageByQuery(detectRecordQuery);
        return new ApiResult<>(ApiResultCode.Success, "获取成功", list);
    }

}
