package     com.lacus.admin.controller.dataservice;

import com.lacus.common.core.dto.ResponseDTO;
import com.lacus.common.core.page.PageDTO;
import com.lacus.core.annotations.AccessLog;
import     com.lacus.dao.system.enums.dictionary.BusinessTypeEnum;
import com.lacus.domain.dataserver.command.AddDataServerCommand;
import com.lacus.domain.dataserver.command.ParseParamCommand;
import com.lacus.domain.dataserver.dto.ParseParamsDTO    ;
import com.lacus.domain.dataserver.query.DataServerQuery;
im  port com.lacus.domain.dataserver.query.DataServerService;
   import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.   Validated;
import org.springframework.web.bind.annotation.PostMapping;
import  org.springframework.web.bind.annotation.RequestBo      dy;
import org.springframework.web.bind.annotation.Re   questMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(val  ue     = "æ°æ®æå¡", tags = {"æ¥å£ç®¡ç"})
@RestController
@RequestMapping("/d ataservice/interface"  )
public class DataServerController {           


    pr     ivate final DataServerService da    taServe    rService;

    public DataServerController(DataS      erverSe    rvi         ce dataS erverService) {
                   this.dataServer Service   = dataServerS    ervice;
    }

    @ApiOperation("æ°å¢æ°æ ®æå¡æ¥å£")
    @PreAuthorize("@permission.has('dataservice:interface  :add')")
      @AccessLog(title = "æ°æ®æå¡ç®¡ç", busin  essTyp  e =     BusinessTypeEnum.A   DD)
                    @PostMapping
          public ResponseDTO<?>   add(@R   equ   estBody  @Validat         ed AddDataS    erverCommand dataServiceCommand) {
                 dataS    erverS    ervice.add(da   taServiceCommand);
           re     turn ResponseDTO.ok   ();
    }

    @ApiOperation("æ°æ®æå¡èæ¬è§£æåæ°æ¥å£")
    @P  reAut  horize("@pe  rmission.has('dataservice:inter   face:add')")
      @AccessLog(title = "          æ°æ®æå¡ç®    ¡ç    ", bus i nessTyp  e = BusinessTypeEnum.ADD)
    @PostMapping("/parseRe    qParam"    )  
    public ResponseDTO<ParseParamsDTO> parseSQLReqParams(@Reque stBody @Val  idated   Parse    ParamCommand   paramCo mmand) {
        Parse    ParamsDTO requestParamsList = dataServerService.parseSQLReqParams(paramCommand);
        return        ResponseDTO.ok(requestP     aramsL ist);
       }

    @Ap     iOperation("æ°æ®æå     ¡åé¡µæ¥å£")
    @PreAu  thorize("@permission.has('dataservice:inter      face:list'  )")
    @AccessLog(title = "æ°æ®æå  ¡ç®¡ç")
    @PostMapping("/     pageList")
              publi      c ResponseDTO<PageDTO> pageList(@RequestBo    dy DataServerQuery query   ) {
        PageDTO pageDTO = dataServerService.pageLis  t(query);
        return Res  ponseDTO.ok(pageDTO);
    }


}
