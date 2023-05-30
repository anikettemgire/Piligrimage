package com.mytrip.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mytrip.dao.BookBusRepository;
import com.mytrip.helper.Message;
import com.mytrip.modal.Bookbus;
import com.mytrip.modal.BusA;
import com.mytrip.modal.FlightA;
import com.mytrip.modal.HolidayPackage;
import com.mytrip.modal.HotelA;
import com.mytrip.modal.TrainAd;
import com.mytrip.modal.User;
import com.mytrip.service.BusBookService;
import com.mytrip.service.BusService;
import com.mytrip.service.FlightBookService;
import com.mytrip.service.FlightService;
import com.mytrip.service.HolidayPackageService;
import com.mytrip.service.HotelBookService;
import com.mytrip.service.HotelService;
import com.mytrip.service.PackageBookService;
import com.mytrip.service.TrainBookService;
import com.mytrip.service.TrainService;
import com.mytrip.service.Userservice;
import  com.mytrip.modal.BookFlight;
import com.mytrip.modal.BookHotel;
import com.mytrip.modal.BookTrain;
import com.mytrip.modal.BookPackage;
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private Userservice userservice;
	@Autowired
	private HotelService hotelService;

	@Autowired
	private FlightService flightService;

	@Autowired
	private BusService busService;

	@Autowired
	private TrainService trainService;

	@Autowired
	private HolidayPackageService holidayPackageService;
	
	@Autowired
	private BusBookService busBookService;
	
	@Autowired
	private FlightBookService flightBookService;
	
	@Autowired
	private TrainBookService trainBookService;

	@Autowired
	 private HotelBookService hotelBookService2;
	
	@Autowired
	private  PackageBookService packageBookService;
	

	@ModelAttribute
	public void addCommdata(Model m, Principal pr) {
		String username = pr.getName();
		User user = this.userservice.getUser(username);
		m.addAttribute("user", user);
		List<HotelA> hotelA = this.hotelService.getAllDeatilHotel();
		m.addAttribute("hotel", hotelA);
		List<FlightA> flightA = this.flightService.getAllDetailFlight();
		m.addAttribute("flight", flightA);
		List<BusA> busA = this.busService.getAllDataOfBus();
		m.addAttribute("bus", busA);
		List<TrainAd> trainAd = this.trainService.getAllDataOftarin();
		m.addAttribute("train", trainAd);
		List<HolidayPackage> allDeatilPackage = this.holidayPackageService.getAllDeatilPackage();
		m.addAttribute("pacakge", allDeatilPackage);
		                  

	}

	@RequestMapping("/index")
	public String adminDashbord(Model m) {
		m.addAttribute("title", "Admin dashbord");
		long busCount = this.busService.getBusCount();
		m.addAttribute("totalbus", busCount);
		long busCountByPaid = this.busBookService.getBusCountByPaid();
		m.addAttribute("bookbus", busCountByPaid);
		
		 long countFlight = this.flightService.countFlight();
         m.addAttribute("totalflight", countFlight);
         long fligthBookk = this.flightBookService.getCountByPaid();
         m.addAttribute("bookfligth", fligthBookk);
         
            long countTrain = this.trainService.getCountTrain();
            m.addAttribute("totaltrain", countTrain);
            long cuntByPaid = this.trainBookService.getCountByPaid();
            m.addAttribute("booktrain", cuntByPaid);
            
                            long countHotel = this.hotelService.getCountHotel();
                            m.addAttribute("totalhotel", countHotel);
                                            long countByPaidhotel = this.hotelBookService2.getCountByPaid();
                                              m.addAttribute("bookhotel", countByPaidhotel);
                                              
                                              long countPackage = this.holidayPackageService.getCountPackage();
                                              m.addAttribute("totalpackage", countPackage);
                                              
                                                        long bookpackge = this.packageBookService.getCountByPaid();
                                                        m.addAttribute("bookpackage", bookpackge);
                                              
                                                         long countUser = this.userservice.getCountUser();
                                                         m.addAttribute("totaluser", countUser);
                                              
		
		return "Admin/index";
	}

	@GetMapping("/viewPackage")
	public String viewPackage(Model m) {
		m.addAttribute("title", "Package");

		return "Admin/viewpackage";
	}

	@GetMapping("/viewhotels")
	public String viewHotels(Model m) {
		m.addAttribute("title", "Hotels");

		return "Admin/viewhotel";
	}

	@GetMapping("/viewflight")
	public String viewFlight(Model m) {

		m.addAttribute("title", "FlightA");
		return "Admin/viewflight";
	}

	@GetMapping("/viewtrain")
	public String viewTrain(Model m) {

		m.addAttribute("title", "TrainAd");
		return "Admin/viewtrain";
	}

	@GetMapping("/viewbus")
	public String viewBus(Model m) {

		m.addAttribute("title", "BusA");
		return "Admin/viewbus";
	}

	@GetMapping("/viewequriy")
	public String viewEnquiry(Model m) {

		m.addAttribute("title", "Enquiry");
		return "Admin/viewenquries";
	}

	// Bookin Hadnler
	@GetMapping("/bookPackage")
	public String BookPackage(Model m) {
		m.addAttribute("title", "BookPackage");
		
		
		List<BookPackage> bookingAllPacakge = this.packageBookService.getBookingAllPacakge();
m.addAttribute("package", bookingAllPacakge);
		return "Admin/bookpakage";
	}

	@GetMapping("/bookhotels")
	public String BookHotels(Model m) {
		m.addAttribute("title", "BookHotels");
		 List<BookHotel> bookigHotelDetail2 = this.hotelBookService2.getBookigHotelDetail();
        m.addAttribute("getBookHotel", bookigHotelDetail2);
		return "Admin/bookhotel";
	}

	@GetMapping("/bookflight")
	public String BookFlight(Model m) {
		
		   List<BookFlight> bookingFligth = this.flightBookService.getBookingFligth();
         m.addAttribute("fligth", bookingFligth);
		m.addAttribute("title", "BookFlight");
		return "Admin/bookflight";
	}

	@GetMapping("/booktrain")
	public String BookTrain(Model m) {
		
	List<BookTrain>  allDetailTrain= this.trainBookService.getAllDetailTrain();
         m.addAttribute("trainbook", allDetailTrain);
		m.addAttribute("title", "BookTrain");
		return "Admin/booktrain";
	}

	@GetMapping("/bookbus")
	public String BookBus(Model m) {
		

        List<Bookbus> bookingBus = this.busBookService.getBookingBus();
        m.addAttribute("boookbus", bookingBus);
		m.addAttribute("title", "BookBus");
		return "Admin/bookbus";
	}

	// All Adding
	@PostMapping("/addhotel")
	public String addHotel(@ModelAttribute HotelA hotelA, @RequestParam("img") MultipartFile file, HttpSession session,
			Model model) {

		try {
			hotelA.setHotel_img_url(file.getOriginalFilename());
			// get Path The Resouce
			File f = new ClassPathResource("static/image/").getFile();
			// get Path Pass method
			Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			HotelA result = this.hotelService.addHotel(hotelA);
			if (result == null) {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			} else {
				session.setAttribute("msg", new Message("Add Hotel Sucessfull!!!", "alert-success"));

			}

		} catch (Exception e) {

			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));

			e.printStackTrace();
			return "redirect:/admin/index";
		}

		return "redirect:/admin/index";
	}

	@PostMapping("/addflight")
	public String addFlight(@ModelAttribute FlightA flightA, Model model, HttpSession session) {

		try {

			FlightA reslut = this.flightService.saveFlight(flightA);
			if (reslut != null) {

				session.setAttribute("msg", new Message("Add Flight Sucessfull!!!", "alert-success"));

			} else {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			}
		} catch (Exception e) {
			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));
			e.printStackTrace();
			return "redirect:/admin/index";
		}
		return "redirect:/admin/index";

	}

	@PostMapping("/addBus")
	public String addBus(@ModelAttribute BusA busA, Model model, HttpSession session) {

		try {

			BusA reslut = this.busService.saveBus(busA);
			if (reslut != null) {

				session.setAttribute("msg", new Message("Add Bus  Sucessfull!!!", "alert-success"));

			} else {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			}
		} catch (Exception e) {
			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));
			e.printStackTrace();
			System.out.println(e);
			return "redirect:/admin/index";

		}
		return "redirect:/admin/index";

	}

	@PostMapping("/addtrain")
	public String addTrain(@ModelAttribute TrainAd trainAd, Model model, HttpSession session) {

		try {

			TrainAd reslut = this.trainService.saveTrain(trainAd);
			if (reslut != null) {

				session.setAttribute("msg", new Message("Add Train Sucessfull!!!", "alert-success"));

			} else {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			}
		} catch (Exception e) {
			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));
			return "redirect:/admin/index";
		}
		return "redirect:/admin/index";

	}

	@PostMapping("/addpackage")
	public String addPackage(@ModelAttribute HolidayPackage holidayPackage, @RequestParam("image") MultipartFile file,
			HttpSession session, Model model) {

		try {
			holidayPackage.setImageUrl(file.getOriginalFilename());
			// get Path The Resouce
			File f = new ClassPathResource("static/image/").getFile();
			// get Path Pass method
			Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			HolidayPackage result = this.holidayPackageService.addPacakge(holidayPackage);
			if (result == null) {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			} else {
				session.setAttribute("msg", new Message("Add Package Sucessfull!!!", "alert-success"));

			}

		} catch (Exception e) {

			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));
			System.out.println(e);
			e.printStackTrace();
			return "redirect:/admin/index";

		}

		return "redirect:/admin/index";
	}

	@GetMapping("/delete/{bus_id}")
	public String delet(@PathVariable("bus_id") int bus_id, Model m, HttpSession session) {

		this.busService.deleteBus(bus_id);
		session.setAttribute("msg", new Message(" Delete Succesful", "alert-success"));

		return "redirect:/admin/viewbus";
	}

	@GetMapping("/deleteflight/{Flight_id}")
	public String deleteFlight(@PathVariable("Flight_id") int fid, Model m, HttpSession session) {

		this.flightService.deletFlight(fid);
		session.setAttribute("msg", new Message(" Delete Flight Succesful", "alert-success"));
		return "redirect:/admin/viewflight";

	}

	@GetMapping("/deletetrain/{train_id}")
	public String deleteTrain(@PathVariable("train_id") int tid, Model m, HttpSession session) {

		this.trainService.deleteTrain(tid);
		session.setAttribute("msg", new Message(" Delete Train Succesful", "alert-success"));
		return "redirect:/admin/viewtrain";

	}

	@GetMapping("/deletehotel/{hotel_id}")
	public String deleteHotel(@PathVariable("hotel_id") int hid, Model model, HttpSession session) {

		this.hotelService.deletehotel(hid);
		session.setAttribute("msg", new Message(" Delete Hotel Succesful", "alert-success"));
		return "redirect:/admin/viewhotels";
	}

	@GetMapping("deletepackage/{p_id}")
	public String deletepackage(@PathVariable("p_id") int pid, Model m, HttpSession session) {

		this.holidayPackageService.deletePackage(pid);
		session.setAttribute("msg", new Message(" Delete Package Succesful", "alert-success"));
		return "redirect:/admin/viewPackage";
	}

	@PostMapping("/editBus")
	public String editFlight(@ModelAttribute BusA busA, Model model, HttpSession session) {

		try {

			BusA reslut = this.busService.saveBus(busA);
			if (reslut != null) {

				session.setAttribute("msg", new Message("Edit  Sucessfull!!!", "alert-success"));

			} else {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			}
		} catch (Exception e) {
			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));
			e.printStackTrace();
			System.out.println(e);
			return "redirect:/admin/viewbus";

		}
		return "redirect:/admin/viewbus";

	}

	@PostMapping("/editpackage")
	public String editPackage(@ModelAttribute HolidayPackage holidayPackage, @RequestParam("image") MultipartFile file,
			HttpSession session, Model model) {

		try {
			holidayPackage.setImageUrl(file.getOriginalFilename());
			// get Path The Resouce
			File f = new ClassPathResource("static/image/").getFile();
			// get Path Pass method
			Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			HolidayPackage result = this.holidayPackageService.addPacakge(holidayPackage);
			if (result == null) {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			} else {
				session.setAttribute("msg", new Message("Edit Package Sucessfull!!!", "alert-success"));

			}

		} catch (Exception e) {

			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));
			System.out.println(e);
			e.printStackTrace();
			return "redirect:/admin/viewPackage";

		}

		return "redirect:/admin/viewPackage";
	}

	@PostMapping("/edittrain")
	public String editTrain(@ModelAttribute TrainAd trainAd, Model model, HttpSession session) {

		try {

			TrainAd reslut = this.trainService.saveTrain(trainAd);
			if (reslut != null) {

				session.setAttribute("msg", new Message("Edit Train Sucessfull!!!", "alert-success"));

			} else {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			}
		} catch (Exception e) {
			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));
			return "redirect:/admin/viewtrain";
		}
		return "redirect:/admin/viewtrain";

	}

	@PostMapping("/editflight")
	public String editFlight(@ModelAttribute FlightA flightA, Model model, HttpSession session) {

		try {

			FlightA reslut = this.flightService.saveFlight(flightA);
			if (reslut != null) {

				session.setAttribute("msg", new Message("edit Flight Sucessfull!!!", "alert-success"));

			} else {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			}
		} catch (Exception e) {
			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));
			e.printStackTrace();
			return "redirect:/admin/viewflight";
		}
		return "redirect:/admin/viewflight";

	}

	@PostMapping("/edithotel")
	public String editHotel(@ModelAttribute HotelA hotelA, @RequestParam("img") MultipartFile file, HttpSession session,
			Model model) {

		try {
			hotelA.setHotel_img_url(file.getOriginalFilename());
			// get Path The Resouce
			File f = new ClassPathResource("static/image/").getFile();
			// get Path Pass method
			Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			HotelA result = this.hotelService.addHotel(hotelA);
			if (result == null) {
				session.setAttribute("msg", new Message("Something Wrong!!!", "alert-success"));
			} else {
				session.setAttribute("msg", new Message("edit Hotel Sucessfull!!!", "alert-success"));

			}

		} catch (Exception e) {

			session.setAttribute("msg", new Message("Server Internal Problem!!!", "alert-danger"));

			e.printStackTrace();
			return "redirect:/admin/viewhotels";
		}

		return "redirect:/admin/viewhotels";
	}

}
