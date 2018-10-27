package com.swcommodities.wsmill.controller;

import java.beans.PropertyEditorSupport;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.swcommodities.wsmill.application.service.CompanyService;
import com.swcommodities.wsmill.application.service.MenuService;
import com.swcommodities.wsmill.el.DateConstant;
import com.swcommodities.wsmill.hibernate.dto.Category;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CompanyTypeMaster;
import com.swcommodities.wsmill.hibernate.dto.Country;
import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.LocationMaster;
import com.swcommodities.wsmill.hibernate.dto.OriginMaster;
import com.swcommodities.wsmill.hibernate.dto.PIType;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.PortMaster;
import com.swcommodities.wsmill.hibernate.dto.QualificationCompany;
import com.swcommodities.wsmill.hibernate.dto.QualityMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingLineCompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingLineMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CategoryRepository;
import com.swcommodities.wsmill.repository.CompanyRepository;
import com.swcommodities.wsmill.repository.CompanyTypeMasterRepository;
import com.swcommodities.wsmill.repository.CountryRepository;
import com.swcommodities.wsmill.repository.CourierMasterRepository;
import com.swcommodities.wsmill.repository.GradeRepository;
import com.swcommodities.wsmill.repository.LocationMasterRepository;
import com.swcommodities.wsmill.repository.OriginRepository;
import com.swcommodities.wsmill.repository.PITypeRepository;
import com.swcommodities.wsmill.repository.PackingRepository;
import com.swcommodities.wsmill.repository.PageRepository;
import com.swcommodities.wsmill.repository.PortRepository;
import com.swcommodities.wsmill.repository.QualificationCompanyRepository;
import com.swcommodities.wsmill.repository.QualityRepository;
import com.swcommodities.wsmill.repository.ShippingLineCompanyMasterRepository;
import com.swcommodities.wsmill.repository.ShippingLineRepository;
import com.swcommodities.wsmill.repository.UserRepository;
import com.swcommodities.wsmill.utils.Common;
import org.springframework.web.servlet.HandlerMapping;

@ControllerAdvice
@PropertySource("classpath:WEB-INF/config.properties")
public class GlobalControllerAdvice {

    @Resource(name = "configConfigurer")
    private Properties configConfigurer;
    
    @Autowired private HttpServletRequest request;
    
    private static final Logger logger = Logger.getLogger(GlobalControllerAdvice.class);
    
    @Autowired CompanyService companyService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private PackingRepository packingRepository;

    @Autowired
    private ShippingLineRepository shippingLineRepository;

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private LocationMasterRepository locationMasterRepository;

    @Autowired
    private QualificationCompanyRepository qualificationCompanyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PITypeRepository piTypeRepository;

    @Autowired
    private QualityRepository qualityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CourierMasterRepository courierMasterRepository;

    @Autowired
    private CompanyTypeMasterRepository companyTypeMasterRepository;

    @Autowired
    private ShippingLineCompanyMasterRepository shippingLineCompanyMasterRepository;
    
    @Autowired PageRepository pageRepository;
    @Autowired MenuService menuService;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Common.date_format_a);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Category.class, "option.category", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Category ch = categoryRepository.findOne(Long.parseLong(text));
                setValue(ch);
            }
        });
        PropertyEditorSupport companySupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                CompanyMaster ch = companyRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        PropertyEditorSupport shippingLineCompanyMasterSupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                ShippingLineCompanyMaster ch = shippingLineCompanyMasterRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        PropertyEditorSupport courierSupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                CourierMaster ch = courierMasterRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        PropertyEditorSupport gradeSupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                GradeMaster ch = gradeRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        PropertyEditorSupport countrySupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                Country ch = countryRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        PropertyEditorSupport shippingLineSupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                ShippingLineMaster ch = shippingLineRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        PropertyEditorSupport portMasterSupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                PortMaster ch = portRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        final PropertyEditorSupport locationMasterSupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                LocationMaster ch = locationMasterRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        PropertyEditorSupport piTypeSupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text.equals("")) {
                    setValue(null);
                    return;
                }
                PIType ch = piTypeRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };
        binder.registerCustomEditor(ShippingLineCompanyMaster.class, "item.shippingLineCompanyMaster", shippingLineCompanyMasterSupport);
        binder.registerCustomEditor(CourierMaster.class, "item.courierMaster", courierSupport);
        binder.registerCustomEditor(Country.class, "item.country", countrySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.companyMaster", companySupport);
        binder.registerCustomEditor(PIType.class, "item.piType", piTypeSupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.companyMasterByClientId", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.client", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.supplier", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.shipper", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.buyer", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.companyMasterBySupplierId", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.companyMasterByShipperId", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.companyMasterByBuyerId", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.companyMasterByConsigneeId", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.companyMasterByControllerId", companySupport);
        binder.registerCustomEditor(CompanyMaster.class, "item.companyMasterByPledger", companySupport);
        binder.registerCustomEditor(ShippingLineMaster.class, "item.shippingLineMaster", shippingLineSupport);
        binder.registerCustomEditor(LocationMaster.class, "item.locationMaster", locationMasterSupport);
        binder.registerCustomEditor(LocationMaster.class, "item.location", locationMasterSupport);
        binder.registerCustomEditor(PortMaster.class, "item.portMasterByLoadingPortId", portMasterSupport);
        binder.registerCustomEditor(PortMaster.class, "item.portMasterByTransitPortId", portMasterSupport);
        binder.registerCustomEditor(PortMaster.class, "item.portMasterByDischargePortId", portMasterSupport);
        binder.registerCustomEditor(OriginMaster.class, "item.originMaster", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                OriginMaster ch = originRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        });
        binder.registerCustomEditor(OriginMaster.class, "item.origin", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                OriginMaster ch = originRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        });
        binder.registerCustomEditor(List.class, "item.companyTypes", new CustomCollectionEditor(List.class) {
            protected Object convertElement(Object element) {
                if (element != null) {
                    Integer companyTypeId = Integer.parseInt(element.toString());
                    CompanyTypeMaster companyTypeMaster = companyTypeMasterRepository.findOne(companyTypeId);
                    return companyTypeMaster;
                }
                return null;
            }
        });
        binder.registerCustomEditor(QualityMaster.class, "item.qualityMaster", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                QualityMaster ch = qualityRepository.findOne(Long.parseLong(text));
                setValue(ch);
            }
        });
        binder.registerCustomEditor(QualityMaster.class, "item.quality", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                QualityMaster ch = qualityRepository.findOne(Long.parseLong(text));
                setValue(ch);
            }
        });
        binder.registerCustomEditor(GradeMaster.class, "item.gradeMaster", gradeSupport);
        binder.registerCustomEditor(GradeMaster.class, "item.grade", gradeSupport);
        binder.registerCustomEditor(GradeMaster.class, "item.gradeMasterByAllocationGradeId", gradeSupport);
        binder.registerCustomEditor(PackingMaster.class, "item.packingMaster", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                PackingMaster ch = packingRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        });
        binder.registerCustomEditor(QualificationCompany.class, "item.qualificationCompany", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                QualificationCompany ch = qualificationCompanyRepository.findOne(Long.parseLong(text));
                setValue(ch);
            }
        });

        binder.registerCustomEditor(Double.class, "item.tons", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Double ch = Double.valueOf(text);
                setValue(ch);
            }
        });

        PropertyEditorSupport dateSupport = new CustomDateEditor(dateFormat, true);

        binder.registerCustomEditor(Date.class, "item.firstDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.fromDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.toDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.creditDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.lastDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.date", dateSupport);
        binder.registerCustomEditor(Date.class, "item.feederEts", dateSupport);
        binder.registerCustomEditor(Date.class, "item.feederEta", dateSupport);
        binder.registerCustomEditor(Date.class, "item.oceanEts", dateSupport);
        binder.registerCustomEditor(Date.class, "item.oceanEta", dateSupport);
        binder.registerCustomEditor(Date.class, "item.loadDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.closingDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.blDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.etaDate", dateSupport);
        binder.registerCustomEditor(Date.class, "item.sentDate", dateSupport);

        PropertyEditorSupport userSupport = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                User ch = userRepository.findOne(Integer.parseInt(text));
                setValue(ch);
            }
        };

    }

    @ModelAttribute
    public void globalAttributes(Model model) {
        model.addAttribute("version", configConfigurer.getProperty("build.version"));
        model.addAttribute("dateTimeFormat", DateConstant.dateTime);
        model.addAttribute("dateTimeFormatAMPM", DateConstant.dateTimeAMPM);
        model.addAttribute("dateJsFormat", DateConstant.dateOnly);
        model.addAttribute("base_web_url", configConfigurer.getProperty("base_web_url"));
        model.addAttribute("base_static_host", configConfigurer.getProperty("base_static_host"));
        try {
            model.addAttribute("permissonsMap", companyService.getPermissionsMap(((User) request.getSession().getAttribute("user")).getId()));
        } catch(Exception e) {
            model.addAttribute("permissonsMap", new HashMap<>());
        }
        
        model.addAttribute("myuser1", "myuser1");
    }
    
    @ModelAttribute
    public void addPageAttributes(Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if(user != null) {
            model.addAttribute("userFullName", user.getFullName());
        }
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        try {
            model.addAttribute("menus", menuService.getAllMenus(user.getId(), pattern.replace(".htm", "").replace(".*", "").replaceFirst("/", "")));
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception anExc) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        anExc.printStackTrace(pw);
        logger.info(sw.toString());
        anExc.printStackTrace();
    }
}
