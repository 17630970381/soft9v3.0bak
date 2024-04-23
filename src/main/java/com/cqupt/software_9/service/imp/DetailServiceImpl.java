package com.cqupt.software_9.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.software_9.entity.Detail;
import com.cqupt.software_9.mapper.DetailMapper;
import com.cqupt.software_9.service.DetailService;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements DetailService {
}
