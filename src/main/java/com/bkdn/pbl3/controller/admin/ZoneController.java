package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.Zone;
import com.bkdn.pbl3.model.ZoneDto;
import com.bkdn.pbl3.service.ZoneService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/zone")
public class ZoneController {

    @Autowired
    ZoneService zoneService;

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("zone", new ZoneDto());
        return "admin/zone/addOrEdit";
    }

    @GetMapping("edit/{zoneId}")
    public ModelAndView edit(ModelMap model, @PathVariable("zoneId") String zoneId){
        Optional<Zone> opt = zoneService.findById(zoneId);
        ZoneDto dto = new ZoneDto();
        if(opt.isPresent()){
            Zone entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("zone", dto);
            return new ModelAndView("admin/zone/addOrEdit", model);
        }
        model.addAttribute("message", "Zone is not existed");
        return new ModelAndView("forward:/admin/zone",model);
    }

    @GetMapping("delete/{zoneId}")
    public ModelAndView delete(ModelMap model, @PathVariable("zoneId") String zoneId){
        zoneService.deleteById(zoneId);
        model.addAttribute("message", "Zone is deleted!");
        return new ModelAndView("forward:/admin/zone/search", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("zone") ZoneDto dto, BindingResult result)
    {
        if(result.hasErrors()){
            return new ModelAndView("admin/zone/addOrEdit");
        }

        Zone entity = new Zone();
        BeanUtils.copyProperties(dto,entity);
        zoneService.save(entity);
        model.addAttribute("message", "Zone is saved!");
        return new ModelAndView("forward:/admin/zone",model);
    }

    @RequestMapping("")
    public String list(ModelMap model){
        List<Zone> list = zoneService.findAll();
        model.addAttribute("zones", list);
        return "admin/zone/list";
    }

    @GetMapping("search")
    public String search(ModelMap model, @RequestParam(name = "zoneName", required = false) String zoneName){
        List<Zone> list;
        if(StringUtils.hasText(zoneName)){
            list = zoneService.findByZoneNameContaining(zoneName);
        }
        else {
            list = zoneService.findAll();
        }
        model.addAttribute("zones", list);
        return "admin/zone/search";
    }

//    @GetMapping("search/paginated")
//    public String search(ModelMap model,
//                         @RequestParam(name = "zoneName", required = false) String zoneName,
//                         @RequestParam("page") Optional<Integer> page,
//                         @RequestParam("size") Optional<Integer> size){
//        int currentPage = page.orElse(1);
//        int pageSize
//        List<Zone> list;
//        if(StringUtils.hasText(zoneName)){
//            list = zoneService.findByZoneNameContaining(zoneName);
//        }
//        else {
//            list = zoneService.findAll();
//        }
//        model.addAttribute("zones", list);
//        return "admin/zone/search";
//    }

}
