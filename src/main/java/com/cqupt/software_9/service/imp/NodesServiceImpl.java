package com.cqupt.software_9.service.imp;

import com.cqupt.software_9.mapper.NodesMapper;
import com.cqupt.software_9.entity.Nodes;
import com.cqupt.software_9.entity.Relationships;
import com.cqupt.software_9.service.Adapter.NodesServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodesServiceImpl extends NodesServiceAdapter {
    @Autowired
    NodesMapper nodesMapper;


    @Override
    public List<Nodes> getAllNodes() {
        return nodesMapper.getAllNodes();
    }

    @Override
    public List<Relationships> getRelationships() {
        return nodesMapper.getRelationships();
    }
}
