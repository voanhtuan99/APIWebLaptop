package com.example.apiweblaptop.service.impl;


import com.example.apiweblaptop.dto.PhieuNhapDTO;
import com.example.apiweblaptop.dto.PhieuNhapResponseDTO;
import com.example.apiweblaptop.entity.Company;
import com.example.apiweblaptop.entity.PhieuNhap;
import com.example.apiweblaptop.entity.User;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.CompanyRepository;
import com.example.apiweblaptop.repo.PhieuNhapRepository;
import com.example.apiweblaptop.repo.ProductRepository;
import com.example.apiweblaptop.repo.UserRepository;
import com.example.apiweblaptop.service.PhieuNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

//    @Autowired
//    private CTPNhapRepository ctpNhapRepository;

    @Override
    public List<PhieuNhapResponseDTO> retrievePhieuNhaps() {
        List<PhieuNhap> nhaps = nhapRepository.findAll();

        return new PhieuNhapResponseDTO().toListDto(nhaps);
    }

//    @Override
//    public PhieuNhapResponseDTO getPhieuNhap(Long nhapId) throws ResourceNotFoundException {
//        PhieuNhap nhap = nhapRepository.findById(nhapId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id: "+nhapId));
//
//        PhieuNhapResponseDTO phieuNhapResponseDTO = new PhieuNhapResponseDTO();
//
//        phieuNhapResponseDTO.setNhapId(nhap.getNhapId());
//        phieuNhapResponseDTO.setCreateDate(nhap.getCreateDate());
//        phieuNhapResponseDTO.setUsername(nhap.getUser().getUserName());
//        phieuNhapResponseDTO.setStatusName(nhap.getStatus().getStatusName());
//        phieuNhapResponseDTO.setDatId(nhap.getPhieuDat().getDatId());
//
//        List<CTPNhapResponseDTO> ctpNhapResponseDTOS = new ArrayList<>();
//
//        System.out.println("------------------"+nhap.getCtpNhaps().size());
//        nhap.getCtpNhaps().forEach(d -> {
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
//
//        return phieuNhapResponseDTO;
//    }

    @Override
    public PhieuNhapDTO savePN(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(nhapDTO.getIduser()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+nhapDTO.getIduser()));
        Company company = companyRepository.findById(nhapDTO.getIdcompany()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+nhapDTO.getIdcompany()));


        PhieuNhap nhap = new PhieuNhapDTO().dtoToEntity(nhapDTO);
        nhap.setUser(user);
        nhap.setLoaiphieu(nhapDTO.getLoaiphieu());
        nhap.setNgaylapphieu(LocalDate.now());
        nhap.setCompany(company);
        nhap.setStatus(nhapDTO.getStatus());
        return new PhieuNhapDTO().entityToDTO(nhapRepository.save(nhap));
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
