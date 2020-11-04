package com.hyva.idm.pkt.pktMappers;


import com.hyva.idm.pkt.pktBeans.PktPermissionBean;
import com.hyva.idm.pkt.pktRelations.PktPermissions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * krishna
 */
@Component
public class PktPermissionMapper {
    public static PktPermissions mapPktPermissionPojoToEnity(PktPermissionBean pktPermissionPojo){
        PktPermissions pktPermission  = new PktPermissions();
        pktPermission.setPktPermissionId(pktPermissionPojo.getPktPermissionId());
        pktPermission.setKeyAction(pktPermissionPojo.getKeyAction());
        pktPermission.setKeyGroup(pktPermissionPojo.getKeyGroup());
        pktPermission.setKeyName(pktPermissionPojo.getKeyName());
        pktPermission.setKeyPlaceHolder(pktPermissionPojo.getKeyPlaceHolder());
        pktPermission.setKeyPriority(pktPermissionPojo.getKeyPriority());
        pktPermission.setKeyVisibility(pktPermissionPojo.getKeyVisibility());
        pktPermission.setKeySubscription(pktPermissionPojo.getKeySubscription());
        pktPermission.setKeyValidation(pktPermissionPojo.getKeyValidation());
        pktPermission.setKeyTiming(pktPermissionPojo.getKeyTiming());
        pktPermission.setKeyValue(pktPermissionPojo.getKeyValue());
        pktPermission.setKeyReference(pktPermissionPojo.getKeyReference());
        pktPermission.setKeyGroup(pktPermissionPojo.getKeyGroup());
        pktPermission.setSync(pktPermissionPojo.getSync());
        pktPermission.setPermission(pktPermissionPojo.getPermission());
        pktPermission.setDescriptor(pktPermissionPojo.getDescriptor());
        pktPermission.setStatus(pktPermissionPojo.getStatus());
        pktPermission.setColumnPosition(pktPermissionPojo.getColumnPosition());
        pktPermission.setColumnName(pktPermissionPojo.getColumnName());
        pktPermission.setOperation(pktPermissionPojo.getOperation());
        pktPermission.setRoundOff(pktPermissionPojo.getRoundOff());
        pktPermission.setOperator(pktPermissionPojo.getOperator());
        pktPermission.setTableName(pktPermissionPojo.getTableName());
        pktPermission.setMenuKey(pktPermissionPojo.getMenuKey());
        pktPermission.setInputType(pktPermissionPojo.getInputType());
        pktPermission.setTop_pos(pktPermissionPojo.getTop_pos());
        pktPermission.setWidth_pos(pktPermissionPojo.getWidth_pos());
        pktPermission.setHeight_pos(pktPermissionPojo.getHeight_pos());
        pktPermission.setPosition_pos(pktPermissionPojo.getPosition_pos());
        pktPermission.setLeft_pos(pktPermissionPojo.getLeft_pos());
        pktPermission.setSearchKey(pktPermissionPojo.getSearchKey());
        pktPermission.setSubMenuKey(pktPermissionPojo.getSubMenuKey());
        pktPermission.setDragid(pktPermissionPojo.getDragid());
        pktPermission.setAlignment(pktPermissionPojo.getAlignment());
        pktPermission.setMax_Height(pktPermissionPojo.getMax_Height());
        pktPermission.setMax_Width(pktPermissionPojo.getMax_Width());
        pktPermission.setMin_Width(pktPermissionPojo.getMin_Width());
        pktPermission.setFont(pktPermissionPojo.getFont());
        pktPermission.setFont_Size(pktPermissionPojo.getFont_Size());
        pktPermission.setFont_Colour(pktPermissionPojo.getFont_Colour());
        pktPermission.setWeight(pktPermissionPojo.getWeight());
        pktPermission.setLetter_Spacing(pktPermissionPojo.getLetter_Spacing());
        pktPermission.setLine_Height(pktPermissionPojo.getLine_Height());
        pktPermission.setDecoration(pktPermissionPojo.getDecoration());
        pktPermission.setX_Position(pktPermissionPojo.getX_Position());
        pktPermission.setY_Position(pktPermissionPojo.getY_Position());
        pktPermission.setBox_Shadow(pktPermissionPojo.getBox_Shadow());
        pktPermission.setColour(pktPermissionPojo.getColour());
        pktPermission.setBackGround_Colour(pktPermissionPojo.getBackGround_Colour());
        pktPermission.setBorder_Width(pktPermissionPojo.getBorder_Width());
        pktPermission.setBorder_Style(pktPermissionPojo.getBorder_Style());
        pktPermission.setBorder_Colour(pktPermissionPojo.getBorder_Colour());
        pktPermission.setSize(pktPermissionPojo.getSize());
        return  pktPermission;
    }
    public static List<PktPermissionBean> mapEntityToPojo(List<PktPermissions> pkt){
        List<PktPermissionBean> list=new ArrayList<>();
        for(PktPermissions pktper:pkt) {
            PktPermissionBean pojo=new PktPermissionBean();
            pojo.setPktPermissionId(pktper.getPktPermissionId());
            pojo.setKeyAction(pktper.getKeyAction());
            pojo.setKeyGroup(pktper.getKeyGroup());
            pojo.setKeyName(pktper.getKeyName());
            pojo.setKeyPlaceHolder(pktper.getKeyPlaceHolder());
            pojo.setKeyPriority(pktper.getKeyPriority());
            pojo.setKeyVisibility(pktper.getKeyVisibility());
            pojo.setKeySubscription(pktper.getKeySubscription());
            pojo.setKeyValidation(pktper.getKeyValidation());
            pojo.setKeyTiming(pktper.getKeyTiming());
            pojo.setKeyValue(pktper.getKeyValue());
            pojo.setKeyReference(pktper.getKeyReference());
            pojo.setKeyGroup(pktper.getKeyGroup());
            pojo.setSync(pktper.getSync());
            pojo.setPermission(pktper.getPermission());
            pojo.setDescriptor(pktper.getDescriptor());
            pojo.setStatus(pktper.getStatus());
            pojo.setColumnPosition(pktper.getColumnPosition());
            pojo.setColumnName(pktper.getColumnName());
            pojo.setOperation(pktper.getOperation());
            pojo.setRoundOff(pktper.getRoundOff());
            pojo.setMenuKey(pktper.getMenuKey());
            pojo.setOperator(pktper.getOperator());
            pojo.setTableName(pktper.getTableName());
            pojo.setInputType(pktper.getInputType());
            pojo.setTop_pos(pktper.getTop_pos());
            pojo.setWidth_pos(pktper.getWidth_pos());
            pojo.setHeight_pos(pktper.getHeight_pos());
            pojo.setPosition_pos(pktper.getPosition_pos());
            pojo.setLeft_pos(pktper.getLeft_pos());
            pojo.setDragid(pktper.getDragid());
            pojo.setAlignment(pktper.getAlignment());
            pojo.setMax_Height(pktper.getMax_Height());
            pojo.setMax_Width(pktper.getMax_Width());
            pojo.setMin_Width(pktper.getMin_Width());
            pojo.setFont(pktper.getFont());
            pojo.setFont_Size(pktper.getFont_Size());
            pojo.setFont_Colour(pktper.getFont_Colour());
            pojo.setWeight(pktper.getWeight());
            pojo.setLetter_Spacing(pktper.getLetter_Spacing());
            pojo.setLine_Height(pktper.getLine_Height());
            pojo.setDecoration(pktper.getDecoration());
            pojo.setX_Position(pktper.getX_Position());
            pojo.setY_Position(pktper.getY_Position());
            pojo.setBox_Shadow(pktper.getBox_Shadow());
            pojo.setColour(pktper.getColour());
            pojo.setBackGround_Colour(pktper.getBackGround_Colour());
            pojo.setBorder_Width(pktper.getBorder_Width());
            pojo.setBorder_Style(pktper.getBorder_Style());
            pojo.setBorder_Colour(pktper.getBorder_Colour());
            pojo.setSize(pktper.getSize());
            list.add(pojo);

        }
        return list;
    }

}
