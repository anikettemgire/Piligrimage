package com.mytrip.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytrip.helper.Message;
import com.mytrip.modal.BookFlight;
import com.mytrip.modal.BookHotel;
import com.mytrip.modal.BookPackage;
import com.mytrip.modal.BookTrain;
import com.mytrip.modal.Bookbus;
import com.mytrip.modal.BusA;
import com.mytrip.modal.Email;
import com.mytrip.modal.FlightA;
import com.mytrip.modal.HolidayPackage;
import com.mytrip.modal.HotelA;
import com.mytrip.modal.TrainAd;
import com.mytrip.modal.User;
import com.mytrip.service.BusBookService;
import com.mytrip.service.BusService;
import com.mytrip.service.EmailService;
import com.mytrip.service.FlightBookService;
import com.mytrip.service.FlightService;
import com.mytrip.service.HolidayPackageService;
import com.mytrip.service.HotelBookService;
import com.mytrip.service.HotelService;
import com.mytrip.service.PackageBookService;
import com.mytrip.service.TrainBookService;
import com.mytrip.service.TrainService;
import com.mytrip.service.Userservice;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;


@Controller
@RequestMapping("/user")
public class UserController {
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
	private BusBookService bookServicebus;
	@Autowired
	private EmailService emailService;
	@Autowired
	private FlightBookService flightBookService;
	
	@Autowired
	private TrainBookService trainBookService;
	
	@Autowired
	private HotelBookService hotelBookService;
	
	@Autowired
	private PackageBookService packageBookService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	


	@ModelAttribute
	public void addCommdata(Model m, Principal pr) {
		String username = pr.getName();
		User user = this.userservice.getUser(username);

		m.addAttribute("user", user);
		
		
		List<Bookbus> busBookingByUser = this.bookServicebus.getBusBookingByUser(user);
		m.addAttribute("bookbus", busBookingByUser);
		               List<BookFlight> flightBookingByUser = this.flightBookService.getFlightBookingByUser(user);
		              
		                  m.addAttribute("bookflight", flightBookingByUser);
		                   
		                  List<BookTrain> allDetailTrain = this.trainBookService.getTrainBookingByUser(user);
		                 
		                  m.addAttribute("booktrain", allDetailTrain);
		                
		                  
		                                         List<BookHotel> hotekBookingByUser = this.hotelBookService.getHotekBookingByUser(user);
		                                         m.addAttribute("bookhotel", hotekBookingByUser);
		                                         
		                                                                List<BookPackage> holidayBookingByUser = this.packageBookService.getHolidayBookingByUser(user);
		                                                                m.addAttribute("bookpackage", holidayBookingByUser);

	}

	@GetMapping("/index")
	public String packkage(Model m) {
		List<HolidayPackage> deatilPackage = this.holidayPackageService.getAllDeatilPackage();
	
		m.addAttribute("title", "package");
		m.addAttribute("package", deatilPackage);
		return "user/package";
	}

	@GetMapping("/hotel")
	public String Hotel(Model m) {
		m.addAttribute("title", "UserHotel");
		List<HotelA> HotelA = this.hotelService.getAllDeatilHotel();
		m.addAttribute("hotel", HotelA);
		return "user/hotel";
	}

	@GetMapping("/bus")
	public String Bus(Model m) {
		m.addAttribute("title", "Travel-Page");
		List<BusA> allDataOfBus = this.busService.getAllDataOfBus();
		m.addAttribute("bus", allDataOfBus);
		return "user/bus";
	}

	@GetMapping("/flight")
	public String filght(Model m) {
		m.addAttribute("title", "FlightA-Page");

		List<FlightA> allDetailFlight = this.flightService.getAllDetailFlight();

		m.addAttribute("flight", allDetailFlight);
		return "user/Filght";
	}

	@GetMapping("/train")
	public String Train(Model m) {
		m.addAttribute("title", "Train-Page");

		List<TrainAd> allDataOftarin = this.trainService.getAllDataOftarin();
		m.addAttribute("train", allDataOftarin);

		return "user/train";
	}

	

	// Payment Handelter
	@PostMapping("/create_order")
	@ResponseBody
	public String Paymet(@RequestBody Map<String, Object> data, Principal pr, Model model) throws Exception {

		int amount = Integer.parseInt(data.get("amout").toString());
		int noOfSeats = Integer.parseInt(data.get("noseat").toString());
		int busId = Integer.parseInt(data.get("busId").toString());
		String date = data.get("date").toString();
		String adress = data.get("adress").toString();

		var client = new RazorpayClient("rzp_test_0YowFv1OI7umEn", "HusHzJXunuPKRSiAm9HY9Kmp");

		JSONObject ob = new JSONObject();
		ob.put("amount", amount * 100);
		ob.put("currency", "INR");
		ob.put("receipt", "txn_235425");

		// cretating new Order
		Order order = client.Orders.create(ob);

		Bookbus bookbus = new Bookbus();
		Double amontt = (double) amount;
		bookbus.setStatus("created");
		bookbus.setBooking_price(amontt);
		bookbus.setAdress(adress);
		bookbus.setNo_of_seats(noOfSeats);
		bookbus.setDate(date);
		bookbus.setOrderId(order.get("id"));

		BusA bus = this.busService.getBusById(busId);
		User user = (User) model.getAttribute("user");

		bookbus.setBusA(bus);
		bookbus.setUser(user);
		this.bookServicebus.saveBookingBus(bookbus);

		return order.toString();
	}

	@PostMapping("/update_order")
	public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {
		Bookbus busbook = this.bookServicebus.FindByOrderId(data.get("order_id").toString());
		if(busbook !=null) {
			Email email=new Email();
			email.setTo(busbook.getUser().getEmail());
			email.setSubject(" Your Bus Booking Succesful !!!");
		     String html ="<table class=\"body\" border=\"0\" align=\"center\" cellpadding=\"0\"\r\n"
		     		+ "\r\n"
		     		+ "		cellspacing=\"0\">\r\n"
		     		+ "\r\n"
		     		+ "		<tr>\r\n"
		     		+ "\r\n"
		     		+ "			<td class=\"center\" align=\"center\" valign=\"top\">\r\n"
		     		+ "\r\n"
		     		+ "				<center>\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "					<table class=\"container twelve columns\">\r\n"
		     		+ "\r\n"
		     		+ "						<tbody>\r\n"
		     		+ "\r\n"
		     		+ "							<tr>\r\n"
		     		+ "\r\n"
		     		+ "								<td class=\"container-content\">\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "									<table class=\"row\">\r\n"
		     		+ "\r\n"
		     		+ "										<tr>\r\n"
		     		+ "\r\n"
		     		+ "											<td class=\"wrapper last\">\r\n"
		     		+ "\r\n"
		     		+ "												<table class=\"twelve columns grey section-header\">\r\n"
		     		+ "\r\n"
		     		+ "													<tr>\r\n"
		     		+ "\r\n"
		     		+ "														<td class=\"panel\">\r\n"
		     		+ "\r\n"
		     		+ "															<h2 class=\"text-center\">Booking Confirmation</h2>\r\n"
		     		+ "\r\n"
		     		+ "															<h3 class=\"text-center\">\r\n"
		     		+ "\r\n"
		     		+ "																<small>"+busbook.getUser().getName()+"</small>\r\n"
		     		+ "\r\n"
		     		+ "															</h3>\r\n"
		     		+ "\r\n"
		     		+ "															<p class=\"text-center\">\r\n"
		     		+ "\r\n"
		     		+ "																<strong>BUS BOOKING NUMBER - "+busbook.getOrderId()+"</strong> <br>Please\r\n"
		     		+ "\r\n"
		     		+ "																quote this number if speaking with our customer service\r\n"
		     		+ "\r\n"
		     		+ "																representatives.\r\n"
		     		+ "\r\n"
		     		+ "															</p>\r\n"
		     		+ "\r\n"
		     		+ "															<p>&nbsp;</p>\r\n"
		     		+ "\r\n"
		     		+ "														</td>\r\n"
		     		+ "\r\n"
		     		+ "														<td class=\"expander\"></td>\r\n"
		     		+ "\r\n"
		     		+ "													</tr>\r\n"
		     		+ "\r\n"
		     		+ "												</table>\r\n"
		     		+ "\r\n"
		     		+ "											</td>\r\n"
		     		+ "\r\n"
		     		+ "										</tr>\r\n"
		     		+ "\r\n"
		     		+ "									</table>\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "									<table class=\"row\">\r\n"
		     		+ "\r\n"
		     		+ "										<tr>\r\n"
		     		+ "\r\n"
		     		+ "											<td class=\"wrapper last\">\r\n"
		     		+ "\r\n"
		     		+ "												<table class=\"twelve columns section-header\">\r\n"
		     		+ "\r\n"
		     		+ "													<tr>\r\n"
		     		+ "\r\n"
		     		+ "														<td class=\"text-center\">\r\n"
		     		+ "\r\n"
		     		+ "															<h2 class=\"inline\">\r\n"
		     		+ "\r\n"
		     		+ "																<strong>Bus Details</strong>\r\n"
		     		+ "\r\n"
		     		+ "															</h2>\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "														</td>\r\n"
		     		+ "\r\n"
		     		+ "														<td class=\"expander\"></td>\r\n"
		     		+ "\r\n"
		     		+ "													</tr>\r\n"
		     		+ "\r\n"
		     		+ "												</table>\r\n"
		     		+ "\r\n"
		     		+ "											</td>\r\n"
		     		+ "\r\n"
		     		+ "										</tr>\r\n"
		     		+ "\r\n"
		     		+ "									</table>\r\n"
		     		+ "\r\n"
		     		+ "									<table class=\"row\">\r\n"
		     		+ "\r\n"
		     		+ "										<tr>\r\n"
		     		+ "\r\n"
		     		+ "											<td class=\"wrapper\">\r\n"
		     		+ "\r\n"
		     		+ "												<table class=\"twelve columns\">\r\n"
		     		+ "\r\n"
		     		+ "													<tbody>\r\n"
		     		+ "\r\n"
		     		+ "														<tr>\r\n"
		     		+ "\r\n"
		     		+ "															<td class=\"two sub-columns\">\r\n"
		     		+ "\r\n"
		     		+ "																<p>\r\n"
		     		+ "\r\n"
		     		+ "																	<strong>"+busbook.getBusA().getBus_no()+"</strong>\r\n"
		     		+ "\r\n"
		     		+ "																</p>\r\n"
		     		+ "\r\n"
		     		+ "																<p class=\"sub\">Etihad operated by Emirates</p>\r\n"
		     		+ "\r\n"
		     		+ "															</td>\r\n"
		     		+ "\r\n"
		     		+ "															<td class=\"ten sub-columns last\">\r\n"
		     		+ "\r\n"
		     		+ "																<table class=\"row\">\r\n"
		     		+ "\r\n"
		     		+ "																	<tbody>\r\n"
		     		+ "\r\n"
		     		+ "																		<tr>\r\n"
		     		+ "\r\n"
		     		+ "																			<td class=\"wrapper\">\r\n"
		     		+ "\r\n"
		     		+ "																				<table class=\"five columns\">\r\n"
		     		+ "\r\n"
		     		+ "																					<tbody>\r\n"
		     		+ "\r\n"
		     		+ "																						<tr>\r\n"
		     		+ "\r\n"
		     		+ "																							<td>\r\n"
		     		+ "\r\n"
		     		+ "																								<p>"+busbook.getBusA().getDeparture_city()+"</p>\r\n"
		     		+ "\r\n"
		     		+ "																								<p class=\"sub\">"+busbook.getBusA().getDeparture_time()+"\r\n"
		     		+ "\r\n"
		     		+ "																									</p>\r\n"
		     		+ "\r\n"
		     		+ "																								<p class=\"large\">"+busbook.getDate()+"</p>\r\n"
		     		+ "\r\n"
		     		+ "																							</td>\r\n"
		     		+ "\r\n"
		     		+ "																						</tr>\r\n"
		     		+ "\r\n"
		     		+ "																					</tbody>\r\n"
		     		+ "\r\n"
		     		+ "																				</table>\r\n"
		     		+ "\r\n"
		     		+ "																			</td>\r\n"
		     		+ "\r\n"
		     		+ "																			<td class=\"wrapper\">\r\n"
		     		+ "\r\n"
		     		+ "																				<table class=\"five columns\">\r\n"
		     		+ "\r\n"
		     		+ "																					<tbody>\r\n"
		     		+ "\r\n"
		     		+ "																						<tr>\r\n"
		     		+ "\r\n"
		     		+ "																							<td>\r\n"
		     		+ "\r\n"
		     		+ "																								<p>"+busbook.getBusA().getDestination_city()+"</p>\r\n"
		     		+ "\r\n"
		     		+ "																								<p class=\"sub\">"+busbook.getBusA().getDestination_time()+"</p>\r\n"
		     		+ "\r\n"
		     		+ "																								<p class=\"large\">"+busbook.getDate()+"</p>\r\n"
		     		+ "\r\n"
		     		+ "																							</td>\r\n"
		     		+ "\r\n"
		     		+ "																						</tr>\r\n"
		     		+ "\r\n"
		     		+ "																					</tbody>\r\n"
		     		+ "\r\n"
		     		+ "																				</table>\r\n"
		     		+ "\r\n"
		     		+ "																			</td>\r\n"
		     		+ "\r\n"
		     		+ "																			<td class=\"expander\"></td>\r\n"
		     		+ "\r\n"
		     		+ "																		</tr>\r\n"
		     		+ "\r\n"
		     		+ "																	</tbody>\r\n"
		     		+ "\r\n"
		     		+ "																</table>\r\n"
		     		+ "\r\n"
		     		+ "															</td>\r\n"
		     		+ "\r\n"
		     		+ "															<td class=\"expander\"></td>\r\n"
		     		+ "\r\n"
		     		+ "														</tr>\r\n"
		     		+ "\r\n"
		     		+ "													</tbody>\r\n"
		     		+ "\r\n"
		     		+ "												</table>\r\n"
		     		+ "\r\n"
		     		+ "											</td>\r\n"
		     		+ "\r\n"
		     		+ "										</tr>\r\n"
		     		+ "\r\n"
		     		+ "									</table>\r\n"
		     		+ "\r\n"
		     		+ "									<hr> \r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "\r\n"
		     		+ "									<table class=\"row\">\r\n"
		     		+ "\r\n"
		     		+ "										<tr>\r\n"
		     		+ "\r\n"
		     		+ "											<td class=\"wrapper last\">\r\n"
		     		+ "\r\n"
		     		+ "												<table class=\"twelve columns grey\">\r\n"
		     		+ "\r\n"
		     		+ "													<tr>\r\n"
		     		+ "\r\n"
		     		+ "														<td class=\"text-center\">\r\n"
		     		+ "\r\n"
		     		+ "															<p>\r\n"
		     		+ "\r\n"
		     		+ "																Bus Fare Rules: <a href=\"#\">Etihad Rules</a>\r\n"
		     		+ "\r\n"
		     		+ "															</p>\r\n"
		     		+ "\r\n"
		     		+ "															<p>\r\n"
		     		+ "\r\n"
		     		+ "																Mytrip Terms & Conditions: <br />\r\n"
		     		+ "\r\n"
		     		+ "														</td>\r\n"
		     		+ "\r\n"
		     		+ "														<td class=\"expander\"></td>\r\n"
		     		+ "\r\n"
		     		+ "													</tr>\r\n"
		     		+ "\r\n"
		     		+ "												</table>\r\n"
		     		+ "\r\n"
		     		+ "											</td>\r\n"
		     		+ "\r\n"
		     		+ "										</tr>\r\n"
		     		+ "\r\n"
		     		+ "									</table>\r\n"
		     		+ "\r\n"
		     		+ "									<table \r\n"
		     		+ "\r\n"
		     		+ "...";
			email.setMeassage(html);
			this.emailService.sendMail(email);
		}
		busbook.setStatus(data.get("status").toString());
		this.bookServicebus.saveBookingBus(busbook);
		return ResponseEntity.ok(Map.of("msg", "updated"));

	}
	
	
	// Payment Handelter
		@PostMapping("/create_Flightorder")
		@ResponseBody
		public String FligthPaymet(@RequestBody Map<String, Object> data, Principal pr, Model model) throws Exception {

			int amount = Integer.parseInt(data.get("amout").toString());
			int noOfSeats = Integer.parseInt(data.get("noseat").toString());
			int fid = Integer.parseInt(data.get("busId").toString());
			String date = data.get("date").toString();
			

			var client = new RazorpayClient("rzp_test_0YowFv1OI7umEn", "HusHzJXunuPKRSiAm9HY9Kmp");

			JSONObject ob = new JSONObject();
			ob.put("amount", amount * 100);
			ob.put("currency", "INR");
			ob.put("receipt", "txn_235425");

			// cretating new Order
			Order order = client.Orders.create(ob);

		     BookFlight bookFlight=new BookFlight();
			Double amontt = (double) amount;
			 bookFlight.setStatus("created");
			 bookFlight.setBooking_price(amontt);
			
			 bookFlight.setNo_of_seats(noOfSeats);
			 bookFlight.setDate(date);
			 bookFlight.setOrderId(order.get("id"));

			       FlightA flightById = this.flightService.getFlightById(fid);
			User user = (User) model.getAttribute("user");

			bookFlight.setFlightA(flightById);
			bookFlight.setUser(user);
			
			this.flightBookService.bookFlight(bookFlight);
			

			return order.toString();
		}

		
		@PostMapping("/update_Fligthorder")
		public ResponseEntity<?> updateFlightOrder(@RequestBody Map<String, Object> data) {
			BookFlight busbook = this.flightBookService.findByOrderId(data.get("order_id").toString());
			if(busbook !=null) {
				Email email=new Email();
				email.setTo(busbook.getUser().getEmail());
				email.setSubject(" Your Flight Booking Succesful !!!");
			     String html ="<table class=\"body\" border=\"0\" align=\"center\" cellpadding=\"0\"\r\n"
			     		+ "\r\n"
			     		+ "		cellspacing=\"0\">\r\n"
			     		+ "\r\n"
			     		+ "		<tr>\r\n"
			     		+ "\r\n"
			     		+ "			<td class=\"center\" align=\"center\" valign=\"top\">\r\n"
			     		+ "\r\n"
			     		+ "				<center>\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "					<table class=\"container twelve columns\">\r\n"
			     		+ "\r\n"
			     		+ "						<tbody>\r\n"
			     		+ "\r\n"
			     		+ "							<tr>\r\n"
			     		+ "\r\n"
			     		+ "								<td class=\"container-content\">\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "									<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "										<tr>\r\n"
			     		+ "\r\n"
			     		+ "											<td class=\"wrapper last\">\r\n"
			     		+ "\r\n"
			     		+ "												<table class=\"twelve columns grey section-header\">\r\n"
			     		+ "\r\n"
			     		+ "													<tr>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"panel\">\r\n"
			     		+ "\r\n"
			     		+ "															<h2 class=\"text-center\">Booking Confirmation</h2>\r\n"
			     		+ "\r\n"
			     		+ "															<h3 class=\"text-center\">\r\n"
			     		+ "\r\n"
			     		+ "																<small>"+busbook.getUser().getName()+"</small>\r\n"
			     		+ "\r\n"
			     		+ "															</h3>\r\n"
			     		+ "\r\n"
			     		+ "															<p class=\"text-center\">\r\n"
			     		+ "\r\n"
			     		+ "																<strong>Flight BOOKING NUMBER - "+busbook.getOrderId()+"</strong> <br>Please\r\n"
			     		+ "\r\n"
			     		+ "																quote this number if speaking with our customer service\r\n"
			     		+ "\r\n"
			     		+ "																representatives.\r\n"
			     		+ "\r\n"
			     		+ "															</p>\r\n"
			     		+ "\r\n"
			     		+ "															<p>&nbsp;</p>\r\n"
			     		+ "\r\n"
			     		+ "														</td>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "													</tr>\r\n"
			     		+ "\r\n"
			     		+ "												</table>\r\n"
			     		+ "\r\n"
			     		+ "											</td>\r\n"
			     		+ "\r\n"
			     		+ "										</tr>\r\n"
			     		+ "\r\n"
			     		+ "									</table>\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "									<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "										<tr>\r\n"
			     		+ "\r\n"
			     		+ "											<td class=\"wrapper last\">\r\n"
			     		+ "\r\n"
			     		+ "												<table class=\"twelve columns section-header\">\r\n"
			     		+ "\r\n"
			     		+ "													<tr>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"text-center\">\r\n"
			     		+ "\r\n"
			     		+ "															<h2 class=\"inline\">\r\n"
			     		+ "\r\n"
			     		+ "																<strong>Flight Booking Details</strong>\r\n"
			     		+ "\r\n"
			     		+ "															</h2>\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "														</td>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "													</tr>\r\n"
			     		+ "\r\n"
			     		+ "												</table>\r\n"
			     		+ "\r\n"
			     		+ "											</td>\r\n"
			     		+ "\r\n"
			     		+ "										</tr>\r\n"
			     		+ "\r\n"
			     		+ "									</table>\r\n"
			     		+ "\r\n"
			     		+ "									<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "										<tr>\r\n"
			     		+ "\r\n"
			     		+ "											<td class=\"wrapper\">\r\n"
			     		+ "\r\n"
			     		+ "												<table class=\"twelve columns\">\r\n"
			     		+ "\r\n"
			     		+ "													<tbody>\r\n"
			     		+ "\r\n"
			     		+ "														<tr>\r\n"
			     		+ "\r\n"
			     		+ "															<td class=\"two sub-columns\">\r\n"
			     		+ "\r\n"
			     		+ "																<p>\r\n"
			     		+ "\r\n"
			     		+ "																	<strong>"+busbook.getFlightA().getFlight_no()+"</strong>\r\n"
			     		+ "\r\n"
			     		+ "																</p>\r\n"
			     		+ "\r\n"
			     		+ "																<p class=\"sub\">Etihad operated by Emirates</p>\r\n"
			     		+ "\r\n"
			     		+ "															</td>\r\n"
			     		+ "\r\n"
			     		+ "															<td class=\"ten sub-columns last\">\r\n"
			     		+ "\r\n"
			     		+ "																<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "																	<tbody>\r\n"
			     		+ "\r\n"
			     		+ "																		<tr>\r\n"
			     		+ "\r\n"
			     		+ "																			<td class=\"wrapper\">\r\n"
			     		+ "\r\n"
			     		+ "																				<table class=\"five columns\">\r\n"
			     		+ "\r\n"
			     		+ "																					<tbody>\r\n"
			     		+ "\r\n"
			     		+ "																						<tr>\r\n"
			     		+ "\r\n"
			     		+ "																							<td>\r\n"
			     		+ "\r\n"
			     		+ "																								<p>"+busbook.getFlightA().getDeparture_city()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																								<p class=\"sub\">"+busbook.getFlightA().getDeparture_time()+"\r\n"
			     		+ "\r\n"
			     		+ "																									</p>\r\n"
			     		+ "\r\n"
			     		+ "																								<p class=\"large\">"+busbook.getDate()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																							</td>\r\n"
			     		+ "\r\n"
			     		+ "																						</tr>\r\n"
			     		+ "\r\n"
			     		+ "																					</tbody>\r\n"
			     		+ "\r\n"
			     		+ "																				</table>\r\n"
			     		+ "\r\n"
			     		+ "																			</td>\r\n"
			     		+ "\r\n"
			     		+ "																			<td class=\"wrapper\">\r\n"
			     		+ "\r\n"
			     		+ "																				<table class=\"five columns\">\r\n"
			     		+ "\r\n"
			     		+ "																					<tbody>\r\n"
			     		+ "\r\n"
			     		+ "																						<tr>\r\n"
			     		+ "\r\n"
			     		+ "																							<td>\r\n"
			     		+ "\r\n"
			     		+ "																								<p>"+busbook.getFlightA().getDestination_city()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																								<p class=\"sub\">"+busbook.getFlightA().getDestination_time()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																								<p class=\"large\">"+busbook.getDate()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																							</td>\r\n"
			     		+ "\r\n"
			     		+ "																						</tr>\r\n"
			     		+ "\r\n"
			     		+ "																					</tbody>\r\n"
			     		+ "\r\n"
			     		+ "																				</table>\r\n"
			     		+ "\r\n"
			     		+ "																			</td>\r\n"
			     		+ "\r\n"
			     		+ "																			<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "																		</tr>\r\n"
			     		+ "\r\n"
			     		+ "																	</tbody>\r\n"
			     		+ "\r\n"
			     		+ "																</table>\r\n"
			     		+ "\r\n"
			     		+ "															</td>\r\n"
			     		+ "\r\n"
			     		+ "															<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "														</tr>\r\n"
			     		+ "\r\n"
			     		+ "													</tbody>\r\n"
			     		+ "\r\n"
			     		+ "												</table>\r\n"
			     		+ "\r\n"
			     		+ "											</td>\r\n"
			     		+ "\r\n"
			     		+ "										</tr>\r\n"
			     		+ "\r\n"
			     		+ "									</table>\r\n"
			     		+ "\r\n"
			     		+ "									<hr> \r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "									<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "										<tr>\r\n"
			     		+ "\r\n"
			     		+ "											<td class=\"wrapper last\">\r\n"
			     		+ "\r\n"
			     		+ "												<table class=\"twelve columns grey\">\r\n"
			     		+ "\r\n"
			     		+ "													<tr>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"text-center\">\r\n"
			     		+ "\r\n"
			     		+ "															<p>\r\n"
			     		+ "\r\n"
			     		+ "																Bus Fare Rules: <a href=\"#\">Etihad Rules</a>\r\n"
			     		+ "\r\n"
			     		+ "															</p>\r\n"
			     		+ "\r\n"
			     		+ "															<p>\r\n"
			     		+ "\r\n"
			     		+ "																Mytrip Terms & Conditions: <br />\r\n"
			     		+ "\r\n"
			     		+ "														</td>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "													</tr>\r\n"
			     		+ "\r\n"
			     		+ "												</table>\r\n"
			     		+ "\r\n"
			     		+ "											</td>\r\n"
			     		+ "\r\n"
			     		+ "										</tr>\r\n"
			     		+ "\r\n"
			     		+ "									</table>\r\n"
			     		+ "\r\n"
			     		+ "									<table \r\n"
			     		+ "\r\n"
			     		+ "...";
				email.setMeassage(html);
				this.emailService.sendMail(email);
			}
			busbook.setStatus(data.get("status").toString());
			  this.flightBookService.bookFlight(busbook);
			return ResponseEntity.ok(Map.of("msg", "updated"));

		}
		
		
		// Payment Handelter
		@PostMapping("/create_Trainorder")
		@ResponseBody
		public String TrainPaymet(@RequestBody Map<String, Object> data, Principal pr, Model model) throws Exception {

			int amount = Integer.parseInt(data.get("amout").toString());
			int noOfSeats = Integer.parseInt(data.get("noseat").toString());
			int fid = Integer.parseInt(data.get("busId").toString());
			String date = data.get("date").toString();
			

			var client = new RazorpayClient("rzp_test_0YowFv1OI7umEn", "HusHzJXunuPKRSiAm9HY9Kmp");

			JSONObject ob = new JSONObject();
			ob.put("amount", amount * 100);
			ob.put("currency", "INR");
			ob.put("receipt", "txn_235425");

			// cretating new Order
			Order order = client.Orders.create(ob);

		     BookTrain bookFlight=new BookTrain();
			Double amontt = (double) amount;
			 bookFlight.setStatus("created");
			 bookFlight.setBooking_price(amontt);
			
			 bookFlight.setNo_of_seats(noOfSeats);
			 bookFlight.setDate(date);
			 bookFlight.setOrderId(order.get("id"));

			         TrainAd trainById = this.trainService.getTrainById(fid);
			User user = (User) model.getAttribute("user");

			bookFlight.setTrainAd(trainById);
			bookFlight.setUser(user);
			
		
		   this.trainBookService.bookBus(bookFlight);

			return order.toString();
		}

		
		@PostMapping("/update_Trainorder")
		public ResponseEntity<?> updateTrainOrder(@RequestBody Map<String, Object> data) {
			BookTrain busbook =this.trainBookService.FindByOrderId(data.get("order_id").toString());
			if(busbook !=null) {
				Email email=new Email();
				email.setTo(busbook.getUser().getEmail());
				email.setSubject(" Your Train Booking Succesful !!!");
			     String html ="<table class=\"body\" border=\"0\" align=\"center\" cellpadding=\"0\"\r\n"
			     		+ "\r\n"
			     		+ "		cellspacing=\"0\">\r\n"
			     		+ "\r\n"
			     		+ "		<tr>\r\n"
			     		+ "\r\n"
			     		+ "			<td class=\"center\" align=\"center\" valign=\"top\">\r\n"
			     		+ "\r\n"
			     		+ "				<center>\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "					<table class=\"container twelve columns\">\r\n"
			     		+ "\r\n"
			     		+ "						<tbody>\r\n"
			     		+ "\r\n"
			     		+ "							<tr>\r\n"
			     		+ "\r\n"
			     		+ "								<td class=\"container-content\">\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "									<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "										<tr>\r\n"
			     		+ "\r\n"
			     		+ "											<td class=\"wrapper last\">\r\n"
			     		+ "\r\n"
			     		+ "												<table class=\"twelve columns grey section-header\">\r\n"
			     		+ "\r\n"
			     		+ "													<tr>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"panel\">\r\n"
			     		+ "\r\n"
			     		+ "															<h2 class=\"text-center\">Booking Confirmation</h2>\r\n"
			     		+ "\r\n"
			     		+ "															<h3 class=\"text-center\">\r\n"
			     		+ "\r\n"
			     		+ "																<small>"+busbook.getUser().getName()+"</small>\r\n"
			     		+ "\r\n"
			     		+ "															</h3>\r\n"
			     		+ "\r\n"
			     		+ "															<p class=\"text-center\">\r\n"
			     		+ "\r\n"
			     		+ "																<strong>Flight BOOKING NUMBER - "+busbook.getOrderId()+"</strong> <br>Please\r\n"
			     		+ "\r\n"
			     		+ "																quote this number if speaking with our customer service\r\n"
			     		+ "\r\n"
			     		+ "																representatives.\r\n"
			     		+ "\r\n"
			     		+ "															</p>\r\n"
			     		+ "\r\n"
			     		+ "															<p>&nbsp;</p>\r\n"
			     		+ "\r\n"
			     		+ "														</td>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "													</tr>\r\n"
			     		+ "\r\n"
			     		+ "												</table>\r\n"
			     		+ "\r\n"
			     		+ "											</td>\r\n"
			     		+ "\r\n"
			     		+ "										</tr>\r\n"
			     		+ "\r\n"
			     		+ "									</table>\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "									<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "										<tr>\r\n"
			     		+ "\r\n"
			     		+ "											<td class=\"wrapper last\">\r\n"
			     		+ "\r\n"
			     		+ "												<table class=\"twelve columns section-header\">\r\n"
			     		+ "\r\n"
			     		+ "													<tr>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"text-center\">\r\n"
			     		+ "\r\n"
			     		+ "															<h2 class=\"inline\">\r\n"
			     		+ "\r\n"
			     		+ "																<strong>Train Booking Details</strong>\r\n"
			     		+ "\r\n"
			     		+ "															</h2>\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "														</td>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "													</tr>\r\n"
			     		+ "\r\n"
			     		+ "												</table>\r\n"
			     		+ "\r\n"
			     		+ "											</td>\r\n"
			     		+ "\r\n"
			     		+ "										</tr>\r\n"
			     		+ "\r\n"
			     		+ "									</table>\r\n"
			     		+ "\r\n"
			     		+ "									<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "										<tr>\r\n"
			     		+ "\r\n"
			     		+ "											<td class=\"wrapper\">\r\n"
			     		+ "\r\n"
			     		+ "												<table class=\"twelve columns\">\r\n"
			     		+ "\r\n"
			     		+ "													<tbody>\r\n"
			     		+ "\r\n"
			     		+ "														<tr>\r\n"
			     		+ "\r\n"
			     		+ "															<td class=\"two sub-columns\">\r\n"
			     		+ "\r\n"
			     		+ "																<p>\r\n"
			     		+ "\r\n"
			     		+ "																	<strong>"+busbook.getTrainAd().getTrain_no()+"</strong>\r\n"
			     		+ "\r\n"
			     		+ "																</p>\r\n"
			     		+ "\r\n"
			     		+ "																<p class=\"sub\">Etihad operated by Emirates</p>\r\n"
			     		+ "\r\n"
			     		+ "															</td>\r\n"
			     		+ "\r\n"
			     		+ "															<td class=\"ten sub-columns last\">\r\n"
			     		+ "\r\n"
			     		+ "																<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "																	<tbody>\r\n"
			     		+ "\r\n"
			     		+ "																		<tr>\r\n"
			     		+ "\r\n"
			     		+ "																			<td class=\"wrapper\">\r\n"
			     		+ "\r\n"
			     		+ "																				<table class=\"five columns\">\r\n"
			     		+ "\r\n"
			     		+ "																					<tbody>\r\n"
			     		+ "\r\n"
			     		+ "																						<tr>\r\n"
			     		+ "\r\n"
			     		+ "																							<td>\r\n"
			     		+ "\r\n"
			     		+ "																								<p>"+busbook.getTrainAd().getDeparture_city()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																								<p class=\"sub\">"+busbook.getTrainAd().getDeparture_time()+"\r\n"
			     		+ "\r\n"
			     		+ "																									</p>\r\n"
			     		+ "\r\n"
			     		+ "																								<p class=\"large\">"+busbook.getDate()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																							</td>\r\n"
			     		+ "\r\n"
			     		+ "																						</tr>\r\n"
			     		+ "\r\n"
			     		+ "																					</tbody>\r\n"
			     		+ "\r\n"
			     		+ "																				</table>\r\n"
			     		+ "\r\n"
			     		+ "																			</td>\r\n"
			     		+ "\r\n"
			     		+ "																			<td class=\"wrapper\">\r\n"
			     		+ "\r\n"
			     		+ "																				<table class=\"five columns\">\r\n"
			     		+ "\r\n"
			     		+ "																					<tbody>\r\n"
			     		+ "\r\n"
			     		+ "																						<tr>\r\n"
			     		+ "\r\n"
			     		+ "																							<td>\r\n"
			     		+ "\r\n"
			     		+ "																								<p>"+busbook.getTrainAd().getDestination_city()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																								<p class=\"sub\">"+busbook.getTrainAd().getDestination_time()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																								<p class=\"large\">"+busbook.getDate()+"</p>\r\n"
			     		+ "\r\n"
			     		+ "																							</td>\r\n"
			     		+ "\r\n"
			     		+ "																						</tr>\r\n"
			     		+ "\r\n"
			     		+ "																					</tbody>\r\n"
			     		+ "\r\n"
			     		+ "																				</table>\r\n"
			     		+ "\r\n"
			     		+ "																			</td>\r\n"
			     		+ "\r\n"
			     		+ "																			<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "																		</tr>\r\n"
			     		+ "\r\n"
			     		+ "																	</tbody>\r\n"
			     		+ "\r\n"
			     		+ "																</table>\r\n"
			     		+ "\r\n"
			     		+ "															</td>\r\n"
			     		+ "\r\n"
			     		+ "															<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "														</tr>\r\n"
			     		+ "\r\n"
			     		+ "													</tbody>\r\n"
			     		+ "\r\n"
			     		+ "												</table>\r\n"
			     		+ "\r\n"
			     		+ "											</td>\r\n"
			     		+ "\r\n"
			     		+ "										</tr>\r\n"
			     		+ "\r\n"
			     		+ "									</table>\r\n"
			     		+ "\r\n"
			     		+ "									<hr> \r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "\r\n"
			     		+ "									<table class=\"row\">\r\n"
			     		+ "\r\n"
			     		+ "										<tr>\r\n"
			     		+ "\r\n"
			     		+ "											<td class=\"wrapper last\">\r\n"
			     		+ "\r\n"
			     		+ "												<table class=\"twelve columns grey\">\r\n"
			     		+ "\r\n"
			     		+ "													<tr>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"text-center\">\r\n"
			     		+ "\r\n"
			     		+ "															<p>\r\n"
			     		+ "\r\n"
			     		+ "																Bus Fare Rules: <a href=\"#\">Etihad Rules</a>\r\n"
			     		+ "\r\n"
			     		+ "															</p>\r\n"
			     		+ "\r\n"
			     		+ "															<p>\r\n"
			     		+ "\r\n"
			     		+ "																Mytrip Terms & Conditions: <br />\r\n"
			     		+ "\r\n"
			     		+ "														</td>\r\n"
			     		+ "\r\n"
			     		+ "														<td class=\"expander\"></td>\r\n"
			     		+ "\r\n"
			     		+ "													</tr>\r\n"
			     		+ "\r\n"
			     		+ "												</table>\r\n"
			     		+ "\r\n"
			     		+ "											</td>\r\n"
			     		+ "\r\n"
			     		+ "										</tr>\r\n"
			     		+ "\r\n"
			     		+ "									</table>\r\n"
			     		+ "\r\n"
			     		+ "									<table \r\n"
			     		+ "\r\n"
			     		+ "...";
				email.setMeassage(html);
				this.emailService.sendMail(email);
			}
			busbook.setStatus(data.get("status").toString());
			  this.trainBookService.bookBus(busbook);
			return ResponseEntity.ok(Map.of("msg", "updated"));

		}





// Payment Handelter
@PostMapping("/create_hotelorder")
@ResponseBody
public String HotelPaymet(@RequestBody Map<String, Object> data, Principal pr, Model model) throws Exception {

	int amount = Integer.parseInt(data.get("amout").toString());
	int noOfSeats = Integer.parseInt(data.get("noseat").toString());
	int hotelid = Integer.parseInt(data.get("busId").toString());
	String date = data.get("date").toString();
	

	var client = new RazorpayClient("rzp_test_0YowFv1OI7umEn", "HusHzJXunuPKRSiAm9HY9Kmp");

	JSONObject ob = new JSONObject();
	ob.put("amount", amount * 100);
	ob.put("currency", "INR");
	ob.put("receipt", "txn_235425");

	// cretating new Order
	Order order = client.Orders.create(ob);

     BookHotel bookHotel =new BookHotel();
	Double amontt = (double) amount;
	bookHotel.setStatus("created");
	bookHotel.setBooking_price(amontt);
	
	bookHotel.setNo_of_person(noOfSeats);
	 bookHotel.setDate(date);
	 bookHotel.setOrderId(order.get("id"));

	         HotelA hotelById = this.hotelService.findById(hotelid);
	User user = (User) model.getAttribute("user");

	 bookHotel.setHotelA(hotelById);
	 bookHotel.setUser(user);
	

    this.hotelBookService.hotelBook(bookHotel);

	return order.toString();
}


@PostMapping("/update_hotelorder")
public ResponseEntity<?> updateHotelOrder(@RequestBody Map<String, Object> data) {
	BookHotel hotelbook =this.hotelBookService.FindByOrderId(data.get("order_id").toString());
	if(hotelbook !=null) {
		Email email=new Email();
		email.setTo(hotelbook.getUser().getEmail());
		email.setSubject(" Your Hotel Booking Succesful !!!");
	     String html ="<!doctype html>\r\n"
	     		+ "\r\n"
	     		+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "<head>\r\n"
	     		+ "\r\n"
	     		+ "<meta name=\"viewport\" content=\"width=device-width\">\r\n"
	     		+ "\r\n"
	     		+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"
	     		+ "\r\n"
	     		+ "<!-- Turn off iOS phone number autodetect -->\r\n"
	     		+ "\r\n"
	     		+ "<meta name=\"format-detection\" content=\"telephone=no\">\r\n"
	     		+ "\r\n"
	     		+ "<style>\r\n"
	     		+ "\r\n"
	     		+ "body, p {\r\n"
	     		+ "\r\n"
	     		+ "	font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\r\n"
	     		+ "\r\n"
	     		+ "	-webkit-font-smoothing: antialiased;\r\n"
	     		+ "\r\n"
	     		+ "	-webkit-text-size-adjust: none;\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "table {\r\n"
	     		+ "\r\n"
	     		+ "	border-collapse: collapse;\r\n"
	     		+ "\r\n"
	     		+ "	border-spacing: 0;\r\n"
	     		+ "\r\n"
	     		+ "	border: 0;\r\n"
	     		+ "\r\n"
	     		+ "	padding: 0;\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "img {\r\n"
	     		+ "\r\n"
	     		+ "	margin: 0;\r\n"
	     		+ "\r\n"
	     		+ "	padding: 0;\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ ".content {\r\n"
	     		+ "\r\n"
	     		+ "	width: 600px;\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ ".no_text_resize {\r\n"
	     		+ "\r\n"
	     		+ "	-moz-text-size-adjust: none;\r\n"
	     		+ "\r\n"
	     		+ "	-webkit-text-size-adjust: none;\r\n"
	     		+ "\r\n"
	     		+ "	-ms-text-size-adjust: none;\r\n"
	     		+ "\r\n"
	     		+ "	text-size-adjust: none;\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "/* Media Queries */\r\n"
	     		+ "\r\n"
	     		+ "@media all and (max-width: 600px) {\r\n"
	     		+ "\r\n"
	     		+ "	table[class=\"content\"] {\r\n"
	     		+ "\r\n"
	     		+ "		width: 100% !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	tr[class=\"grid-no-gutter\"] td[class=\"grid__col\"] {\r\n"
	     		+ "\r\n"
	     		+ "		padding-left: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-right: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	td[class=\"grid__col\"] {\r\n"
	     		+ "\r\n"
	     		+ "		padding-left: 18px !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-right: 18px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	table[class=\"small_full_width\"] {\r\n"
	     		+ "\r\n"
	     		+ "		width: 100% !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-bottom: 10px;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	a[class=\"header-link\"] {\r\n"
	     		+ "\r\n"
	     		+ "		margin-right: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "		margin-left: 10px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	a[class=\"btn\"] {\r\n"
	     		+ "\r\n"
	     		+ "		width: 100%;\r\n"
	     		+ "\r\n"
	     		+ "		border-left-width: 0px !important;\r\n"
	     		+ "\r\n"
	     		+ "		border-right-width: 0px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	table[class=\"col-layout\"] {\r\n"
	     		+ "\r\n"
	     		+ "		width: 100% !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	td[class=\"col-container\"] {\r\n"
	     		+ "\r\n"
	     		+ "		display: block !important;\r\n"
	     		+ "\r\n"
	     		+ "		width: 100% !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-left: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-right: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	td[class=\"col-nav-items\"] {\r\n"
	     		+ "\r\n"
	     		+ "		display: inline-block !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-left: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-right: 10px !important;\r\n"
	     		+ "\r\n"
	     		+ "		background: none !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	img[class=\"col-img\"] {\r\n"
	     		+ "\r\n"
	     		+ "		height: auto !important;\r\n"
	     		+ "\r\n"
	     		+ "		max-width: 520px !important;\r\n"
	     		+ "\r\n"
	     		+ "		width: 100% !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	td[class=\"col-center-sm\"] {\r\n"
	     		+ "\r\n"
	     		+ "		text-align: center;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	tr[class=\"footer-attendee-cta\"]>td[class=\"grid__col\"] {\r\n"
	     		+ "\r\n"
	     		+ "		padding: 24px 0 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	td[class=\"col-footer-cta\"] {\r\n"
	     		+ "\r\n"
	     		+ "		padding-left: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-right: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	td[class=\"footer-links\"] {\r\n"
	     		+ "\r\n"
	     		+ "		text-align: left !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.hide-for-small {\r\n"
	     		+ "\r\n"
	     		+ "		display: none !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.ribbon-mobile {\r\n"
	     		+ "\r\n"
	     		+ "		line-height: 1.3 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.small_full_width {\r\n"
	     		+ "\r\n"
	     		+ "		width: 100% !important;\r\n"
	     		+ "\r\n"
	     		+ "		padding-bottom: 10px;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.table__ridge {\r\n"
	     		+ "\r\n"
	     		+ "		height: 7px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.table__ridge img {\r\n"
	     		+ "\r\n"
	     		+ "		display: none !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.table__ridge--top {\r\n"
	     		+ "\r\n"
	     		+ "		background-image:\r\n"
	     		+ "\r\n"
	     		+ "			url(https://cdn.evbstatic.com/s3-s3/marketing/emails/modules/ridges_top_fullx2.jpg)\r\n"
	     		+ "\r\n"
	     		+ "			!important;\r\n"
	     		+ "\r\n"
	     		+ "		background-size: 170% 7px;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.table__ridge--bottom {\r\n"
	     		+ "\r\n"
	     		+ "		background-image:\r\n"
	     		+ "\r\n"
	     		+ "			url(https://cdn.evbstatic.com/s3-s3/marketing/emails/modules/ridges_bottom_fullx2.jpg)\r\n"
	     		+ "\r\n"
	     		+ "			!important;\r\n"
	     		+ "\r\n"
	     		+ "		background-size: 170% 7px;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.summary-table__total {\r\n"
	     		+ "\r\n"
	     		+ "		padding-right: 10px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.app-cta {\r\n"
	     		+ "\r\n"
	     		+ "		display: none !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.app-cta__mobile {\r\n"
	     		+ "\r\n"
	     		+ "		width: 100% !important;\r\n"
	     		+ "\r\n"
	     		+ "		height: auto !important;\r\n"
	     		+ "\r\n"
	     		+ "		max-height: none !important;\r\n"
	     		+ "\r\n"
	     		+ "		overflow: visible !important;\r\n"
	     		+ "\r\n"
	     		+ "		float: none !important;\r\n"
	     		+ "\r\n"
	     		+ "		display: block !important;\r\n"
	     		+ "\r\n"
	     		+ "		margin-top: 12px !important;\r\n"
	     		+ "\r\n"
	     		+ "		visibility: visible;\r\n"
	     		+ "\r\n"
	     		+ "		font-size: inherit !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "	/* List Event Cards */\r\n"
	     		+ "\r\n"
	     		+ "	.list-card__header {\r\n"
	     		+ "\r\n"
	     		+ "		width: 130px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.list-card__label {\r\n"
	     		+ "\r\n"
	     		+ "		width: 130px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.list-card__image-wrapper {\r\n"
	     		+ "\r\n"
	     		+ "		width: 130px !important;\r\n"
	     		+ "\r\n"
	     		+ "		height: 65px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.list-card__image {\r\n"
	     		+ "\r\n"
	     		+ "		max-width: 130px !important;\r\n"
	     		+ "\r\n"
	     		+ "		max-height: 65px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.list-card__body {\r\n"
	     		+ "\r\n"
	     		+ "		padding-left: 10px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.list-card__title {\r\n"
	     		+ "\r\n"
	     		+ "		margin-bottom: 10px !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.list-card__date {\r\n"
	     		+ "\r\n"
	     		+ "		padding-top: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "@media all and (device-width: 768px) and (device-height: 1024px) and\r\n"
	     		+ "\r\n"
	     		+ "	(orientation:landscape) {\r\n"
	     		+ "\r\n"
	     		+ "	.ribbon-mobile {\r\n"
	     		+ "\r\n"
	     		+ "		line-height: 1.3 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.ribbon-mobile__text {\r\n"
	     		+ "\r\n"
	     		+ "		padding: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "@media all and (device-width: 768px) and (device-height: 1024px) and\r\n"
	     		+ "\r\n"
	     		+ "	(orientation:portrait) {\r\n"
	     		+ "\r\n"
	     		+ "	.ribbon-mobile {\r\n"
	     		+ "\r\n"
	     		+ "		line-height: 1.3 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.ribbon-mobile__text {\r\n"
	     		+ "\r\n"
	     		+ "		padding: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "@media screen and (min-device-height:480px) and (max-device-height:568px) , (\r\n"
	     		+ "\r\n"
	     		+ "		min-device-width : 375px) and (max-device-width : 667px) and\r\n"
	     		+ "\r\n"
	     		+ "		(-webkit-min-device-pixel-ratio : 2) , ( min-device-width : 414px) and\r\n"
	     		+ "\r\n"
	     		+ "	(max-device-width : 736px) and (-webkit-min-device-pixel-ratio : 3) {\r\n"
	     		+ "\r\n"
	     		+ "	.hide_for_iphone {\r\n"
	     		+ "\r\n"
	     		+ "		display: none !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "	.passbook {\r\n"
	     		+ "\r\n"
	     		+ "		width: auto !important;\r\n"
	     		+ "\r\n"
	     		+ "		height: auto !important;\r\n"
	     		+ "\r\n"
	     		+ "		line-height: auto !important;\r\n"
	     		+ "\r\n"
	     		+ "		visibility: visible !important;\r\n"
	     		+ "\r\n"
	     		+ "		display: block !important;\r\n"
	     		+ "\r\n"
	     		+ "		max-height: none !important;\r\n"
	     		+ "\r\n"
	     		+ "		overflow: visible !important;\r\n"
	     		+ "\r\n"
	     		+ "		float: none !important;\r\n"
	     		+ "\r\n"
	     		+ "		text-indent: 0 !important;\r\n"
	     		+ "\r\n"
	     		+ "		font-size: inherit !important;\r\n"
	     		+ "\r\n"
	     		+ "	}\r\n"
	     		+ "\r\n"
	     		+ "}\r\n"
	     		+ "\r\n"
	     		+ "</style>\r\n"
	     		+ "\r\n"
	     		+ "</head>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "<body border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\"\r\n"
	     		+ "\r\n"
	     		+ "	width=\"100%\" bgcolor=\"#F7F7F7\" style=\"margin: 0;\">\r\n"
	     		+ "\r\n"
	     		+ "	<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\"\r\n"
	     		+ "\r\n"
	     		+ "		width=\"100%\" bgcolor=\"#F7F7F7\">\r\n"
	     		+ "\r\n"
	     		+ "		<tr>\r\n"
	     		+ "\r\n"
	     		+ "			<td style=\"padding-right: 10px; padding-left: 10px;\">\r\n"
	     		+ "\r\n"
	     		+ "			\r\n"
	     		+ "\r\n"
	     		+ "				\r\n"
	     		+ "\r\n"
	     		+ "                </td>\r\n"
	     		+ "\r\n"
	     		+ "              </tr>\r\n"
	     		+ "\r\n"
	     		+ "            </table>\r\n"
	     		+ "\r\n"
	     		+ "          <![endif]-->\r\n"
	     		+ "\r\n"
	     		+ "			</td>\r\n"
	     		+ "\r\n"
	     		+ "		</tr>\r\n"
	     		+ "\r\n"
	     		+ "		<tr>\r\n"
	     		+ "\r\n"
	     		+ "			<td>\r\n"
	     		+ "\r\n"
	     		+ "				\r\n"
	     		+ "\r\n"
	     		+ "				<table class=\"content\" align=\"center\" cellpadding=\"0\"\r\n"
	     		+ "\r\n"
	     		+ "					cellspacing=\"0\" border=\"0\" bgcolor=\"#F7F7F7\"\r\n"
	     		+ "\r\n"
	     		+ "					style=\"width: 600px; max-width: 600px;\">\r\n"
	     		+ "\r\n"
	     		+ "					<tr>\r\n"
	     		+ "\r\n"
	     		+ "						<td colspan=\"2\" style=\"background: #fff; border-radius: 8px;\">\r\n"
	     		+ "\r\n"
	     		+ "							<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
	     		+ "\r\n"
	     		+ "								<tr>\r\n"
	     		+ "\r\n"
	     		+ "									<td\r\n"
	     		+ "\r\n"
	     		+ "										style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\">\r\n"
	     		+ "\r\n"
	     		+ "								<tr class=\"\">\r\n"
	     		+ "\r\n"
	     		+ "									<td class=\"grid__col\"\r\n"
	     		+ "\r\n"
	     		+ "										style=\"font-family: 'Helvetica neue', Helvetica, arial, sans-serif; padding: 32px 40px;\">\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "										<h2\r\n"
	     		+ "\r\n"
	     		+ "											style=\"color: #404040; font-weight: 300; margin: 0 0 12px 0; font-size: 20px; line-height: 30px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif;\">\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "											Hi {"+hotelbook.getUser().getName()+"},</h2>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "										<p\r\n"
	     		+ "\r\n"
	     		+ "											style=\"color: #666666; font-weight: 400; font-size: 15px; line-height: 21px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif;\"\r\n"
	     		+ "\r\n"
	     		+ "											class=\"\">Your reservation request for {{"+hotelbook.getHotelA().getHotel_name()+"}},\r\n"
	     		+ "\r\n"
	     		+ "											{{"+hotelbook.getHotelA().getHotel_location()+"}} has been confirmed. Please review the details of\r\n"
	     		+ "\r\n"
	     		+ "											your booking.</p>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "										<table width=\"100%\" border=\"2\" cellspacing=\"0\" cellpadding=\"0\"\r\n"
	     		+ "\r\n"
	     		+ "											style=\"margin-top: 12px; margin-bottom: 12px; margin: 24px 0; color: #666666; font-weight: 400; font-size: 15px; line-height: 21px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif;\">\r\n"
	     		+ "\r\n"
	     		+ "											<tr>\r\n"
	     		+ "\r\n"
	     		+ "												<td\r\n"
	     		+ "\r\n"
	     		+ "													style=\"padding: 20px 20px 0px; font-weight: 700; font-size: 25px;\">\r\n"
	     		+ "\r\n"
	     		+ "													\r\n"
	     		+ "\r\n"
	     		+ "												<p\r\n"
	     		+ "\r\n"
	     		+ "														style=\"padding-top: -5px; font-weight: 700; font-size: 12px;\">Booking\r\n"
	     		+ "\r\n"
	     		+ "														Confirmation Code: {{"+hotelbook.getOrderId()+"}}</p>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "												</td>\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "											<tr>\r\n"
	     		+ "\r\n"
	     		+ "												\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "											<tr>\r\n"
	     		+ "\r\n"
	     		+ "												<td\r\n"
	     		+ "\r\n"
	     		+ "													style=\"padding: 20px 20px 10px; font-weight: 700; font-size: 18px;\">{{"+hotelbook.getHotelA().getHotel_name()+"}}\r\n"
	     		+ "\r\n"
	     		+ "													<p\r\n"
	     		+ "\r\n"
	     		+ "														style=\"padding-top: 0px; font-weight: 700; font-size: 12px;\">{{"+hotelbook.getHotelA().getNoofroom()+"\r\n"
	     		+ "\r\n"
	     		+ "														Rooms}} {{Room Type Name}} - {{"+hotelbook.getHotelA().getNo_of_person()+"}} Guests</p>\r\n"
	     		+ "\r\n"
	     		+ "												</td>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "											<tr>\r\n"
	     		+ "\r\n"
	     		+ "												<td></td>\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "											<tr>\r\n"
	     		+ "\r\n"
	     		+ "												\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "											<tr>\r\n"
	     		+ "\r\n"
	     		+ "												\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "											<tr>\r\n"
	     		+ "\r\n"
	     		+ "												<td>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "													<table style=\"width: 100%;!important\">\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "														\r\n"
	     		+ "\r\n"
	     		+ "														\r\n"
	     		+ "\r\n"
	     		+ "														\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "														\r\n"
	     		+ "\r\n"
	     		+ "														\r\n"
	     		+ "\r\n"
	     		+ "														<tr>\r\n"
	     		+ "\r\n"
	     		+ "															<td\r\n"
	     		+ "\r\n"
	     		+ "																style=\"padding: 5px 20px 10px 20px; font-weight: 700; font-size: 14px; color: #000\">Grand\r\n"
	     		+ "\r\n"
	     		+ "																Total</td>\r\n"
	     		+ "\r\n"
	     		+ "															<td></td>\r\n"
	     		+ "\r\n"
	     		+ "															<td\r\n"
	     		+ "\r\n"
	     		+ "																style=\"padding: 5px 20px 10px 30px; font-weight: 700; font-size: 14px; color: #000;\">₹\r\n"
	     		+ "\r\n"
	     		+ "																"+hotelbook.getBooking_price()+"</td>\r\n"
	     		+ "\r\n"
	     		+ "														</tr>\r\n"
	     		+ "\r\n"
	     		+ "														<tr>\r\n"
	     		+ "\r\n"
	     		+ "															<td\r\n"
	     		+ "\r\n"
	     		+ "																style=\"padding: 5px 20px 10px 20px; font-weight: 700; font-size: 14px;\">Payment\r\n"
	     		+ "\r\n"
	     		+ "																Mode</td>\r\n"
	     		+ "\r\n"
	     		+ "															<td></td>\r\n"
	     		+ "\r\n"
	     		+ "															<td\r\n"
	     		+ "\r\n"
	     		+ "																style=\"padding: 5px 20px 10px 30px; font-weight: 700; font-size: 14px;\">Pay\r\n"
	     		+ "\r\n"
	     		+ "																@ Online</td>\r\n"
	     		+ "\r\n"
	     		+ "														</tr>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "														</tr>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "													</table>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "												</td>\r\n"
	     		+ "\r\n"
	     		+ "											<tr>\r\n"
	     		+ "\r\n"
	     		+ "												<td\r\n"
	     		+ "\r\n"
	     		+ "													style=\"padding: 20px 20px 10px; font-weight: 700; font-size: 18px;\">Details\r\n"
	     		+ "\r\n"
	     		+ "													<p\r\n"
	     		+ "\r\n"
	     		+ "														style=\"padding-top: 0px; font-weight: 700; font-size: 12px;\">\r\n"
	     		+ "\r\n"
	     		+ "													<ul\r\n"
	     		+ "\r\n"
	     		+ "														style=\"padding-top: 0px; font-weight: 300; font-size: 14px;\">\r\n"
	     		+ "\r\n"
	     		+ "														<li>"+hotelbook.getHotelA().getHotel_descrption()+"</li>\r\n"
	     		+ "\r\n"
	     		+ "														\r\n"
	     		+ "\r\n"
	     		+ "													</ul>\r\n"
	     		+ "\r\n"
	     		+ "													</p>\r\n"
	     		+ "\r\n"
	     		+ "												</td>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "											<td></td>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "											</tr>\r\n"
	     		+ "\r\n"
	     		+ "										</table> \r\n"
	     		+ "\r\n"
	     		+ "										<p\r\n"
	     		+ "\r\n"
	     		+ "											style=\"color: #666666; font-weight: 400; font-size: 15px; line-height: 21px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif;\"\r\n"
	     		+ "\r\n"
	     		+ "											class=\"\">Hope you enjoyed the booking experience and will\r\n"
	     		+ "\r\n"
	     		+ "											like the stay too.</p>\r\n"
	     		+ "\r\n"
	     		+ "										<p\r\n"
	     		+ "\r\n"
	     		+ "											style=\"color: #666666; font-weight: 400; font-size: 17px; line-height: 24px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif; margin-bottom: 6px; margin-top: 24px;\"\r\n"
	     		+ "\r\n"
	     		+ "											class=\"\">Cheers,</p>\r\n"
	     		+ "\r\n"
	     		+ "										<p\r\n"
	     		+ "\r\n"
	     		+ "											style=\"color: #666666; font-weight: 400; font-size: 17px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif; margin-bottom: 6px; margin-top: 10px;\">Hotel\r\n"
	     		+ "\r\n"
	     		+ "											Direct Team</p>\r\n"
	     		+ "\r\n"
	     		+ "									</td>\r\n"
	     		+ "\r\n"
	     		+ "								</tr>\r\n"
	     		+ "\r\n"
	     		+ "								</td>\r\n"
	     		+ "\r\n"
	     		+ "								</tr>\r\n"
	     		+ "\r\n"
	     		+ "							</table>\r\n"
	     		+ "\r\n"
	     		+ "						</td>\r\n"
	     		+ "\r\n"
	     		+ "					</tr>\r\n"
	     		+ "\r\n"
	     		+ "				</table> \r\n"
	     		+ "\r\n"
	     		+ "			</td>\r\n"
	     		+ "\r\n"
	     		+ "		</tr>\r\n"
	     		+ "\r\n"
	     		+ "	</table>\r\n"
	     		+ "\r\n"
	     		+ "</body>\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "\r\n"
	     		+ "</html>\r\n"
	     		+ "\r\n"
	     		+ "";
		email.setMeassage(html);
		this.emailService.sendMail(email);
	}
	hotelbook.setStatus(data.get("status").toString());
	 this.hotelBookService.hotelBook(hotelbook);
	return ResponseEntity.ok(Map.of("msg", "updated"));

}




// Payment Handelter
@PostMapping("/create_packorder")
@ResponseBody
public String PackagePaymet(@RequestBody Map<String, Object> data, Principal pr, Model model) throws Exception {

	int amount = Integer.parseInt(data.get("amout").toString());
	int noOfSeats = Integer.parseInt(data.get("noseat").toString());
	int pid = Integer.parseInt(data.get("pid").toString());
	String date = data.get("date").toString();
	String adress = data.get("adress").toString();

	var client = new RazorpayClient("rzp_test_0YowFv1OI7umEn", "HusHzJXunuPKRSiAm9HY9Kmp");

	JSONObject ob = new JSONObject();
	ob.put("amount", amount * 100);
	ob.put("currency", "INR");
	ob.put("receipt", "txn_235425");

	// cretating new Order
	Order order = client.Orders.create(ob);

	BookPackage bookPackage =new BookPackage();
	Double amontt = (double) amount;
	bookPackage.setStatus("created");
	bookPackage.setBooking_price(amontt);
	bookPackage.setAdress(adress);
	bookPackage.setNo_of_person(noOfSeats);
	bookPackage.setDate(date);
	bookPackage.setOrderId(order.get("id"));
                 HolidayPackage pacakge = this.holidayPackageService.getPacakge(pid);

	User user = (User) model.getAttribute("user");

	bookPackage.setHolidayPackage(pacakge);
	bookPackage.setUser(user);
	this.packageBookService.bookPackage(bookPackage);


	return order.toString();
}

@PostMapping("/update_packorder")
public ResponseEntity<?> updatePacakgeOrder(@RequestBody Map<String, Object> data) {
	BookPackage packagebook = this.packageBookService.FindByOrderId(data.get("order_id").toString());
	if(packagebook !=null) {
		Email email=new Email();
		email.setTo(packagebook.getUser().getEmail());
		email.setSubject(" Pacakge Booking Succesful !!!");
		String html = "<!doctype html>\r\n"

					+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"

					+ "\r\n"

					+ "<head>\r\n"

					+ "<meta name=\"viewport\" content=\"width=device-width\">\r\n"

					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"

					+ "<!-- Turn off iOS phone number autodetect -->\r\n"

					+ "<meta name=\"format-detection\" content=\"telephone=no\">\r\n"

					+ "<style>\r\n"

					+ "body, p {\r\n"

					+ "	font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\r\n"

					+ "	-webkit-font-smoothing: antialiased;\r\n"

					+ "	-webkit-text-size-adjust: none;\r\n"

					+ "}\r\n"

					+ "\r\n"

					+ "table {\r\n"

					+ "	border-collapse: collapse;\r\n"

					+ "	border-spacing: 0;\r\n"

					+ "	border: 0;\r\n"

					+ "	padding: 0;\r\n"

					+ "}\r\n"

					+ "\r\n"

					+ "img {\r\n"

					+ "	margin: 0;\r\n"

					+ "	padding: 0;\r\n"

					+ "}\r\n"

					+ "\r\n"

					+ ".content {\r\n"

					+ "	width: 600px;\r\n"

					+ "}\r\n"

					+ "\r\n"

					+ ".no_text_resize {\r\n"

					+ "	-moz-text-size-adjust: none;\r\n"

					+ "	-webkit-text-size-adjust: none;\r\n"

					+ "	-ms-text-size-adjust: none;\r\n"

					+ "	text-size-adjust: none;\r\n"

					+ "}\r\n"

					+ "\r\n"

					+ "/* Media Queries */\r\n"

					+ "@media all and (max-width: 600px) {\r\n"

					+ "	table[class=\"content\"] {\r\n"

					+ "		width: 100% !important;\r\n"

					+ "	}\r\n"

					+ "	tr[class=\"grid-no-gutter\"] td[class=\"grid__col\"] {\r\n"

					+ "		padding-left: 0 !important;\r\n"

					+ "		padding-right: 0 !important;\r\n"

					+ "	}\r\n"

					+ "	td[class=\"grid__col\"] {\r\n"

					+ "		padding-left: 18px !important;\r\n"

					+ "		padding-right: 18px !important;\r\n"

					+ "	}\r\n"

					+ "	table[class=\"small_full_width\"] {\r\n"

					+ "		width: 100% !important;\r\n"

					+ "		padding-bottom: 10px;\r\n"

					+ "	}\r\n"

					+ "	a[class=\"header-link\"] {\r\n"

					+ "		margin-right: 0 !important;\r\n"

					+ "		margin-left: 10px !important;\r\n"

					+ "	}\r\n"

					+ "	a[class=\"btn\"] {\r\n"

					+ "		width: 100%;\r\n"

					+ "		border-left-width: 0px !important;\r\n"

					+ "		border-right-width: 0px !important;\r\n"

					+ "	}\r\n"

					+ "	table[class=\"col-layout\"] {\r\n"

					+ "		width: 100% !important;\r\n"

					+ "	}\r\n"

					+ "	td[class=\"col-container\"] {\r\n"

					+ "		display: block !important;\r\n"

					+ "		width: 100% !important;\r\n"

					+ "		padding-left: 0 !important;\r\n"

					+ "		padding-right: 0 !important;\r\n"

					+ "	}\r\n"

					+ "	td[class=\"col-nav-items\"] {\r\n"

					+ "		display: inline-block !important;\r\n"

					+ "		padding-left: 0 !important;\r\n"

					+ "		padding-right: 10px !important;\r\n"

					+ "		background: none !important;\r\n"

					+ "	}\r\n"

					+ "	img[class=\"col-img\"] {\r\n"

					+ "		height: auto !important;\r\n"

					+ "		max-width: 520px !important;\r\n"

					+ "		width: 100% !important;\r\n"

					+ "	}\r\n"

					+ "	td[class=\"col-center-sm\"] {\r\n"

					+ "		text-align: center;\r\n"

					+ "	}\r\n"

					+ "	tr[class=\"footer-attendee-cta\"]>td[class=\"grid__col\"] {\r\n"

					+ "		padding: 24px 0 0 !important;\r\n"

					+ "	}\r\n"

					+ "	td[class=\"col-footer-cta\"] {\r\n"

					+ "		padding-left: 0 !important;\r\n"

					+ "		padding-right: 0 !important;\r\n"

					+ "	}\r\n"

					+ "	td[class=\"footer-links\"] {\r\n"

					+ "		text-align: left !important;\r\n"

					+ "	}\r\n"

					+ "	.hide-for-small {\r\n"

					+ "		display: none !important;\r\n"

					+ "	}\r\n"

					+ "	.ribbon-mobile {\r\n"

					+ "		line-height: 1.3 !important;\r\n"

					+ "	}\r\n"

					+ "	.small_full_width {\r\n"

					+ "		width: 100% !important;\r\n"

					+ "		padding-bottom: 10px;\r\n"

					+ "	}\r\n"

					+ "	.table__ridge {\r\n"

					+ "		height: 7px !important;\r\n"

					+ "	}\r\n"

					+ "	.table__ridge img {\r\n"

					+ "		display: none !important;\r\n"

					+ "	}\r\n"

					+ "	.table__ridge--top {\r\n"

					+ "		background-image:\r\n"

					+ "			url(https://cdn.evbstatic.com/s3-s3/marketing/emails/modules/ridges_top_fullx2.jpg)\r\n"

					+ "			!important;\r\n"

					+ "		background-size: 170% 7px;\r\n"

					+ "	}\r\n"

					+ "	.table__ridge--bottom {\r\n"

					+ "		background-image:\r\n"

					+ "			url(https://cdn.evbstatic.com/s3-s3/marketing/emails/modules/ridges_bottom_fullx2.jpg)\r\n"

					+ "			!important;\r\n"

					+ "		background-size: 170% 7px;\r\n"

					+ "	}\r\n"

					+ "	.summary-table__total {\r\n"

					+ "		padding-right: 10px !important;\r\n"

					+ "	}\r\n"

					+ "	.app-cta {\r\n"

					+ "		display: none !important;\r\n"

					+ "	}\r\n"

					+ "	.app-cta__mobile {\r\n"

					+ "		width: 100% !important;\r\n"

					+ "		height: auto !important;\r\n"

					+ "		max-height: none !important;\r\n"

					+ "		overflow: visible !important;\r\n"

					+ "		float: none !important;\r\n"

					+ "		display: block !important;\r\n"

					+ "		margin-top: 12px !important;\r\n"

					+ "		visibility: visible;\r\n"

					+ "		font-size: inherit !important;\r\n"

					+ "	}\r\n"

					+ "\r\n"

					+ "	/* List Event Cards */\r\n"

					+ "	.list-card__header {\r\n"

					+ "		width: 130px !important;\r\n"

					+ "	}\r\n"

					+ "	.list-card__label {\r\n"

					+ "		width: 130px !important;\r\n"

					+ "	}\r\n"

					+ "	.list-card__image-wrapper {\r\n"

					+ "		width: 130px !important;\r\n"

					+ "		height: 65px !important;\r\n"

					+ "	}\r\n"

					+ "	.list-card__image {\r\n"

					+ "		max-width: 130px !important;\r\n"

					+ "		max-height: 65px !important;\r\n"

					+ "	}\r\n"

					+ "	.list-card__body {\r\n"

					+ "		padding-left: 10px !important;\r\n"

					+ "	}\r\n"

					+ "	.list-card__title {\r\n"

					+ "		margin-bottom: 10px !important;\r\n"

					+ "	}\r\n"

					+ "	.list-card__date {\r\n"

					+ "		padding-top: 0 !important;\r\n"

					+ "	}\r\n"

					+ "}\r\n"

					+ "\r\n"

					+ "@media all and (device-width: 768px) and (device-height: 1024px) and\r\n"

					+ "	(orientation:landscape) {\r\n"

					+ "	.ribbon-mobile {\r\n"

					+ "		line-height: 1.3 !important;\r\n"

					+ "	}\r\n"

					+ "	.ribbon-mobile__text {\r\n"

					+ "		padding: 0 !important;\r\n"

					+ "	}\r\n"

					+ "}\r\n"

					+ "\r\n"

					+ "@media all and (device-width: 768px) and (device-height: 1024px) and\r\n"

					+ "	(orientation:portrait) {\r\n"

					+ "	.ribbon-mobile {\r\n"

					+ "		line-height: 1.3 !important;\r\n"

					+ "	}\r\n"

					+ "	.ribbon-mobile__text {\r\n"

					+ "		padding: 0 !important;\r\n"

					+ "	}\r\n"

					+ "}\r\n"

					+ "\r\n"

					+ "@media screen and (min-device-height:480px) and (max-device-height:568px) , (\r\n"

					+ "		min-device-width : 375px) and (max-device-width : 667px) and\r\n"

					+ "		(-webkit-min-device-pixel-ratio : 2) , ( min-device-width : 414px) and\r\n"

					+ "	(max-device-width : 736px) and (-webkit-min-device-pixel-ratio : 3) {\r\n"

					+ "	.hide_for_iphone {\r\n"

					+ "		display: none !important;\r\n"

					+ "	}\r\n"

					+ "	.passbook {\r\n"

					+ "		width: auto !important;\r\n"

					+ "		height: auto !important;\r\n"

					+ "		line-height: auto !important;\r\n"

					+ "		visibility: visible !important;\r\n"

					+ "		display: block !important;\r\n"

					+ "		max-height: none !important;\r\n"

					+ "		overflow: visible !important;\r\n"

					+ "		float: none !important;\r\n"

					+ "		text-indent: 0 !important;\r\n"

					+ "		font-size: inherit !important;\r\n"

					+ "	}\r\n"

					+ "}\r\n"

					+ "</style>\r\n"

					+ "</head>\r\n"

					+ "\r\n"

					+ "\r\n"

					+ "<body border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\"\r\n"

					+ "	width=\"100%\" bgcolor=\"#F7F7F7\" style=\"margin: 0;\">\r\n"

					+ "	<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\"\r\n"

					+ "		width=\"100%\" bgcolor=\"#F7F7F7\">\r\n"

					+ "		<tr>\r\n"

					+ "			<td style=\"padding-right: 10px; padding-left: 10px;\">\r\n"

					+ "			\r\n"

					+ "				\r\n"

					+ "                </td>\r\n"

					+ "              </tr>\r\n"

					+ "            </table>\r\n"

					+ "          <![endif]-->\r\n"

					+ "			</td>\r\n"

					+ "		</tr>\r\n"

					+ "		<tr>\r\n"

					+ "			<td>\r\n"

					+ "				\r\n"

					+ "				<table class=\"content\" align=\"center\" cellpadding=\"0\"\r\n"

					+ "					cellspacing=\"0\" border=\"0\" bgcolor=\"#F7F7F7\"\r\n"

					+ "					style=\"width: 600px; max-width: 600px;\">\r\n"

					+ "					<tr>\r\n"

					+ "						<td colspan=\"2\" style=\"background: #fff; border-radius: 8px;\">\r\n"

					+ "							<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"

					+ "								<tr>\r\n"

					+ "									<td\r\n"

					+ "										style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\">\r\n"

					+ "								<tr class=\"\">\r\n"

					+ "									<td class=\"grid__col\"\r\n"

					+ "										style=\"font-family: 'Helvetica neue', Helvetica, arial, sans-serif; padding: 32px 40px;\">\r\n"

					+ "\r\n"

					+ "										<h2\r\n"

					+ "											style=\"color: #404040; font-weight: 300; margin: 0 0 12px 0; font-size: 20px; line-height: 30px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif;\">\r\n"

					+ "\r\n"

					+ "											Hi {"+packagebook.getUser().getName()+"},</h2>\r\n"

					+ "\r\n"

					+ "										<p\r\n"

					+ "											style=\"color: #666666; font-weight: 400; font-size: 15px; line-height: 21px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif;\"\r\n"

					+ "											class=\"\">Your reservation request for {{"+packagebook.getHolidayPackage().getPackage_name()+"}},\r\n"

															

					+ "											your booking.</p>\r\n"

					+ "\r\n"

					+ "										<table width=\"100%\" border=\"2\" cellspacing=\"0\" cellpadding=\"0\"\r\n"

					+ "											style=\"margin-top: 12px; margin-bottom: 12px; margin: 24px 0; color: #666666; font-weight: 400; font-size: 15px; line-height: 21px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif;\">\r\n"

					+ "											<tr>\r\n"

					+ "												<td\r\n"

					+ "													style=\"padding: 20px 20px 0px; font-weight: 700; font-size: 25px;\">\r\n"

					+ "													\r\n"

					+ "												<p\r\n"

					+ "														style=\"padding-top: -5px; font-weight: 700; font-size: 12px;\">Booking\r\n"

					+ "														Confirmation Code: {{"+packagebook.getOrderId()+"}}</p>\r\n"

					+ "\r\n"

					+ "												</td>\r\n"

					+ "											</tr>\r\n"

					+ "											<tr>\r\n"

					+ "												\r\n"

					+ "											</tr>\r\n"

					+ "											<tr>\r\n"

																

					+ "\r\n"

					+ "											</tr>\r\n"

					+ "											<tr>\r\n"

					+ "												<td></td>\r\n"

					+ "											</tr>\r\n"

					+ "											<tr>\r\n"

					+ "												\r\n"

					+ "											</tr>\r\n"

					+ "											<tr>\r\n"

					+ "												\r\n"

					+ "											</tr>\r\n"

					+ "											<tr>\r\n"

					+ "												<td>\r\n"

					+ "\r\n"

					+ "													<table style=\"width: 100%;!important\">\r\n"

					+ "\r\n"

					+ "														\r\n"

					+ "														\r\n"

					+ "														\r\n"

					+ "\r\n"

					+ "														\r\n"

					+ "														\r\n"

					+ "														<tr>\r\n"

					+ "															<td\r\n"

					+ "																style=\"padding: 5px 20px 10px 20px; font-weight: 700; font-size: 14px; color: #000\">Grand\r\n"

					+ "																Total</td>\r\n"

					+ "															<td></td>\r\n"

					+ "															<td\r\n"

					+ "																style=\"padding: 5px 20px 10px 30px; font-weight: 700; font-size: 14px; color: #000;\">₹\r\n"

					+ "																"+packagebook.getBooking_price()+"</td>\r\n"

					+ "														</tr>\r\n"

					+ "														<tr>\r\n"

					+ "															<td\r\n"

					+ "																style=\"padding: 5px 20px 10px 20px; font-weight: 700; font-size: 14px;\">Payment\r\n"

					+ "																Mode</td>\r\n"

					+ "															<td></td>\r\n"

					+ "															<td\r\n"

					+ "																style=\"padding: 5px 20px 10px 30px; font-weight: 700; font-size: 14px;\">Pay\r\n"

					+ "																@ Online</td>\r\n"

					+ "														</tr>\r\n"

					+ "\r\n"

					+ "\r\n"

					+ "														</tr>\r\n"

					+ "\r\n"

					+ "\r\n"

					+ "													</table>\r\n"

					+ "\r\n"

					+ "\r\n"

					+ "												</td>\r\n"

					+ "											<tr>\r\n"

					+ "												<td\r\n"

					+ "													style=\"padding: 20px 20px 10px; font-weight: 700; font-size: 18px;\">Details\r\n"

					+ "													<p\r\n"

					+ "														style=\"padding-top: 0px; font-weight: 700; font-size: 12px;\">\r\n"

					+ "													<ul\r\n"

					+ "														style=\"padding-top: 0px; font-weight: 300; font-size: 14px;\">\r\n"

					+ "														<li>"+packagebook.getHolidayPackage().getDescription()+"</li>\r\n"

					+ "														\r\n"

					+ "													</ul>\r\n"

					+ "													</p>\r\n"

					+ "												</td>\r\n"

					+ "\r\n"

					+ "											</tr>\r\n"

					+ "\r\n"

					+ "\r\n"

					+ "											<td></td>\r\n"

					+ "\r\n"

					+ "\r\n"

					+ "											</tr>\r\n"

					+ "\r\n"

					+ "											</tr>\r\n"

					+ "										</table> \r\n"

					+ "										<p\r\n"

					+ "											style=\"color: #666666; font-weight: 400; font-size: 15px; line-height: 21px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif;\"\r\n"

					+ "											class=\"\">Hope you enjoyed the booking experience and will\r\n"

					+ "											like the stay too.</p>\r\n"

					+ "										<p\r\n"

					+ "											style=\"color: #666666; font-weight: 400; font-size: 17px; line-height: 24px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif; margin-bottom: 6px; margin-top: 24px;\"\r\n"

					+ "											class=\"\">Cheers,</p>\r\n"

					+ "										<p\r\n"

					+ "											style=\"color: #666666; font-weight: 400; font-size: 17px; font-family: 'Helvetica neue', Helvetica, arial, sans-serif; margin-bottom: 6px; margin-top: 10px;\">Hotel\r\n"

					+ "											Direct Team</p>\r\n"

					+ "									</td>\r\n"

					+ "								</tr>\r\n"

					+ "								</td>\r\n"

					+ "								</tr>\r\n"

					+ "							</table>\r\n"

					+ "						</td>\r\n"

					+ "					</tr>\r\n"

					+ "				</table> \r\n"

					+ "			</td>\r\n"

					+ "		</tr>\r\n"

					+ "	</table>\r\n"

					+ "</body>\r\n"

					+ "\r\n"

					+ "</html>";
		email.setMeassage(html);
		this.emailService.sendMail(email);
	}
	packagebook.setStatus(data.get("status").toString());
	this.packageBookService.bookPackage(packagebook);
	return ResponseEntity.ok(Map.of("msg", "updated"));

}

@PostMapping("/change")
public String ChnagePasword(@RequestParam("old") String old,@RequestParam("new") String NewPassword,HttpSession Session,Model model){
	
	 
	   // gte the user by using the user repository
	     User user =  (User) model.getAttribute("user");
	    
	                      String upass = user.getPassword();
	                     
	            if(this.bCryptPasswordEncoder.matches(old, upass)) {
	            	//passencry karun save karnar
	            	user.setPassword(this.bCryptPasswordEncoder.encode(NewPassword));
	            	this.userservice.userSave(user);

	            	//chanhe the password here if user password(mens old) and GUI password old checcj
	                  Session.setAttribute("msg", "Change Password Succesful");
	            	
	            }else {
	            	
	            	Session.setAttribute("msg", "Invalid Current Password try again!!!");
	            	//error denar
	            	return "redirect:/user/index";
	            }
	
	
	return "redirect:/user/index";
}


  @GetMapping("/cancleBus/{bus_id}") public String delet(@PathVariable("bus_id")
  int bus_id, Model m, HttpSession session) {
	  
	  this.bookServicebus.cancleBus(bus_id);
	  session.setAttribute("msg", "BusBooking Cancel Succesful");
	 	return "redirect:/user/index";
	  
	  
  }
  
  
  @GetMapping("/cancleflight/{Flight_id}") public String
  deleteFlight(@PathVariable("Flight_id") int fid, Model m, HttpSession
  session) {
  
  this.flightBookService.cancleFlight(fid);
		  session.setAttribute("msg", "FlightBooking Cancel Succesful");
	return "redirect:/user/index";
  
  }
  
  @GetMapping("/cancletrain/{train_id}") public String
  deleteTrain(@PathVariable("train_id") int tid, Model m, HttpSession session)
  {
  
  this.trainBookService.cancleTrain(tid);
  
  session.setAttribute("msg", "TrainBooking Cancel Succesful");
	return "redirect:/user/index";
  }
 

@GetMapping("/canclehotel/{bookhotel_id}")
public String deleteHotel(@PathVariable("bookhotel_id") int hid, Model model, HttpSession session) {

	this.hotelBookService.cancleHotel(hid);
	  session.setAttribute("msg", "Hotel Cancel Succesful");
	return "redirect:/user/index";
}


  @GetMapping("/canclpackage/{bookpakage_id}")
  public String deletepackage(@PathVariable("bookpakage_id") int pid, Model m, HttpSession session) {
  
   this.packageBookService.cancleByPackage(pid);
   session.setAttribute("msg", "Pacakage Cancel Succesful");
 	return "redirect:/user/index";
   

 
       
}
}