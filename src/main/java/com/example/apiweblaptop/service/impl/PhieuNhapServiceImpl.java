package com.example.apiweblaptop.service.impl;


import com.example.apiweblaptop.dto.*;
import com.example.apiweblaptop.entity.*;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.*;
import com.example.apiweblaptop.service.PhieuNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PhieuNhapServiceImpl implements PhieuNhapService {
    @Autowired
    private PhieuNhapRepository nhapRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private CTPhieuNhapRepository ctpNhapRepository;

    @Autowired
    private ImageProductRepository imageProductRepository;
    @Override
    public List<PhieuNhapResponseDTO> retrievePhieuNhaps() {
        List<PhieuNhap> nhaps = nhapRepository.findAll();
        return new PhieuNhapResponseDTO().toListDto(nhaps);
    }

    @Override
    public PhieuNhapResponseDTO getPhieuNhap(Long nhapId) throws ResourceNotFoundException {
        PhieuNhap nhap = nhapRepository.findById(nhapId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id: "+nhapId));

        PhieuNhapResponseDTO phieuNhapResponseDTO = new PhieuNhapResponseDTO().convertToDto(nhap);

        phieuNhapResponseDTO.setNhapId(nhap.getId());
        phieuNhapResponseDTO.setCreateDate(nhap.getNgaylapphieu());
        phieuNhapResponseDTO.setEmail(nhap.getUser().getEmail());
        phieuNhapResponseDTO.setStatusName(nhap.getStatus());
        List<ProductImage> imgdtos = imageProductRepository.findAll();
        List<CTPhieuNhap> ctpNhapResponseDTOS =ctpNhapRepository.findAll() ;
        List<CTPhieuNhapResponseDTO> listPNSet = new ArrayList<>();
        for(int i = 0; i< ctpNhapResponseDTOS.size(); i++) {
            if (ctpNhapResponseDTOS.get(i).getPhieuNhap().getId() == phieuNhapResponseDTO.getNhapId()) {
                Product product = productrepository.getById(ctpNhapResponseDTOS.get(i).getProduct().getId());
                CTPhieuNhapResponseDTO ctdto = new CTPhieuNhapResponseDTO().convertToDto(ctpNhapResponseDTOS.get(i));
                List <ProductImage> imgs = new ArrayList<>();
                ctdto.setProductName(product.getProductName());
                imgdtos.forEach(e -> {
                    if(e.getProduct().getId() == product.getId()) {
                        imgs.add(e);
                    }
                });
                ctdto.setImageDTOS(new ImageDTO().toListDto(imgs));
                listPNSet.add(ctdto);
            }
        }
        phieuNhapResponseDTO.setCtpNhapResponseDTOS(listPNSet);
//        phieuNhapResponseDTO.forEach(d -> {
//            CTPNhapResponseDTO ctpNhapResponseDTO = new CTPNhapResponseDTO();
//
//            Product product = productrepository.getById(d.getCtpnId().getProductId());
//            ctpNhapResponseDTO.setProductName(product.getProductName());
//
//            List<ImageDTO> dtos = new ArrayList<>();
//
//            if(product.getProductImages()!=null){
//                product.getProductImages().forEach(e -> {
//                    dtos.add(new ImageDTO().convertToDto(e));
//                });
//            }
//            ctpNhapResponseDTO.setId(d.getCtpnId());
//            ctpNhapResponseDTO.setImageDTOS(dtos);
//            ctpNhapResponseDTO.setQtyNhap(d.getQtyNhap());
//            ctpNhapResponseDTO.setPriceNhap(d.getPriceNhap());
//
//            ctpNhapResponseDTOS.add(ctpNhapResponseDTO);
//        });
//
//
//        phieuNhapResponseDTO.setCtpNhapResponseDTOS(ctpNhapResponseDTOS);

        return phieuNhapResponseDTO;
    }

    @Override
    public PhieuNhapResponseDTO savePN(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(nhapDTO.getIduser()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+nhapDTO.getIduser()));
        Company company = companyRepository.findById(nhapDTO.getIdcompany()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+nhapDTO.getIdcompany()));


        PhieuNhap nhap = new PhieuNhapDTO().dtoToEntity(nhapDTO);
        nhap.setUser(user);
        nhap.setLoaiphieu("Phiếu nhập");
        nhap.setNgaylapphieu(LocalDate.now());
        nhap.setCompany(company);
        nhap.setStatus(nhapDTO.getStatus());
        return new PhieuNhapResponseDTO().convertToDto(nhapRepository.save(nhap));
    }



    @Override
    public PhieuNhapDTO cancelPN(Long id) throws ResourceNotFoundException {
        PhieuNhap phieuNhap = nhapRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+id));
        List<CTPhieuNhap> ctPhieuNhaps = ctpNhapRepository.findAll();
        List<CTPhieuNhapDTO> ctpns = new ArrayList<>();
        ctPhieuNhaps.forEach(e -> {
            if(phieuNhap.getId() == e.getPhieuNhap().getId()) {
                ctpns.add(new CTPhieuNhapDTO().entityToDTO(e));
            }
        });
        phieuNhap.setStatus("Cancel");
        List<Product> products = productrepository.findAll();
        for(int i=0; i < ctpns.size(); i++) {
            for(int j=0; j<products.size(); j++) {
                if(ctpns.get(i).getIdproduct() == products.get(j).getId()) {
                    if(phieuNhap.getLoaiphieu().equals("Phiếu nhập")) {
                        products.get(j).setQty(products.get(j).getQty()-ctpns.get(i).getQuantity());
                    }
                    else if(phieuNhap.getLoaiphieu().equals("Phiếu xuất")) {
                        products.get(j).setQty(products.get(j).getQty()+ctpns.get(i).getQuantity());
                        products.get(j).setProduct_sold(products.get(j).getProduct_sold()-ctpns.get(i).getQuantity());
                    }
                }
                productrepository.save(products.get(j));
            }
        }
        return new PhieuNhapDTO().entityToDTO(phieuNhap);
    }

    @Override
    public PhieuNhapResponseDTO savePX(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(nhapDTO.getIduser()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+nhapDTO.getIduser()));
        Company company = companyRepository.findById(nhapDTO.getIdcompany()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+nhapDTO.getIdcompany()));


        PhieuNhap nhap = new PhieuNhapDTO().dtoToEntity(nhapDTO);
        nhap.setUser(user);
        nhap.setLoaiphieu("Phiếu xuất");
        nhap.setNgaylapphieu(LocalDate.now());
        nhap.setCompany(company);
        nhap.setStatus(nhapDTO.getStatus());
        return new PhieuNhapResponseDTO().convertToDto(nhapRepository.save(nhap));
    }

    @Override
    public Boolean deletePN(Long nhapId) throws ResourceNotFoundException {
        PhieuNhap nhap = nhapRepository.findById(nhapId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+nhapId));
        this.nhapRepository.delete(nhap);
        return true;
    }

    @Override
    public PhieuNhapDTO updatePN(PhieuNhapDTO dto, Long id) throws ResourceNotFoundException {
        PhieuNhap nhapExist = nhapRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+id));
        User user = userRepository.findById(dto.getIduser()).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+dto.getIduser()));
        Company company = companyRepository.findById(dto.getIdcompany()).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+dto.getIdcompany()));
        nhapExist.setNgaylapphieu(nhapExist.getNgaylapphieu());
        nhapExist.setLoaiphieu(dto.getLoaiphieu());
        nhapExist.setCompany(company);
        nhapExist.setUser(user);
        nhapExist.setStatus(dto.getStatus());
        PhieuNhap nhap = new PhieuNhap();

        nhap = nhapRepository.save(nhapExist);
        return new PhieuNhapDTO().entityToDTO(nhap);
    }

//    @Override
//    public PhieuNhapDTO updateStatusPN(Long nhapId, String status) throws ResourceNotFoundException {
//        PhieuNhap nhapExist = nhapRepository.findById(nhapId).orElseThrow(() ->
//                new ResourceNotFoundException("phieu nhap not found for this id: "+nhapId));
//
//        if(status.equals("waiting receipt")){
//            Status stt = statusRepository.findAllByStatusName(status);
//            nhapExist.setStatus(stt);
//            //Status sttDat = statusRepository.findAllByStatusName("complete");
//            nhapExist.getPhieuDat().setStatus(stt);
//            datRepository.save(nhapExist.getPhieuDat());
//        } else if(status.equals("receipted")){
//            Status stt = statusRepository.findAllByStatusName(status);
//            nhapExist.setStatus(stt);
//        }
//        else if(status.equals("cancel")) {
//            Status stt = statusRepository.findAllByStatusName(status);
//            nhapExist.setStatus(stt);
//
//            //Status sttDat = statusRepository.findAllByStatusName("no receipt");
//            nhapExist.getPhieuDat().setStatus(stt);
//            datRepository.save(nhapExist.getPhieuDat());
//
//        } else if(status.equals("complete")) {
//            Status stt = statusRepository.findAllByStatusName(status);
//            nhapExist.setStatus(stt);
//            List<CTPNhap> ctpNhapList = ctpNhapRepository.findAllByCtpnIdNhapId(nhapId);
//            ctpNhapList.forEach(n -> {
//                Product product2 = productrepository.getById(n.getCtpnId().getProductId());
//                System.out.println("product 2 id ========"+n.getCtpnId().getProductId());
//                product2.setProductQty(product2.getProductQty() + n.getQtyNhap());
//                productrepository.save(product2);
//                System.out.println("sl sau khi add"+product2.getProductQty());
//            });
//            nhapExist.getPhieuDat().setStatus(stt);
//            datRepository.save(nhapExist.getPhieuDat());
//        }
//
//        PhieuNhap nhap = new PhieuNhap();
//        nhap = nhapRepository.save(nhapExist);
//
//        return new PhieuNhapDTO().convertToDto(nhap);
//    }
}
