
const toggleSlidebar= () => {
	if($('.side').is(":visible")){
		$(".side").css("display","none");
		$(".content").css("margin-left","0");
		
	}else{
		$(".side").css("display","block");
		$(".content").css("margin-left","20%");
	}
};

const paymentStart=()=>{
    console.log("stared");
	let amout=$("#add-sum").html();
		let date=$("#dc").val();
		let noseat=$("#num-one").val();
		let adress=$("#adr").val();
		let busId=$("#buid").val();
	
		
		
	
	console.log(amout);
	//amout jar empty kiva null asel tar alert yenar ani retur manje tynatar kaich nai honar
	if(amout=='' || amout==null ||amout <=0 || date=='' || date==null || noseat=='' || noseat==null|| adress=='' || adress==null || busId=='' || busId==null){
		//alert("amout is requried");
		swal("Something Wrong!", "Please Enter Deatil!", "error");
		return ;
	}
	//will use ajax to creta order to server by using jqury version min.js 


	$.ajax(
		{
        //create url to sent the request to the server with data
		url:'/user/create_order',
	
		
					
		// ha data ahe apla
		data:JSON.stringify({amout:amout,date:date,noseat:noseat,adress:adress,busId:busId,info:'order_request'}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response){
			//this function invoked succes 
			console.log(response);
			//check if Resonse Apn handle Mahuds Return kel aahe tho Ala Ka Manun
			if(response.status=='created'){
				//asel tar apn form Open Karun Shakto
				//open Payment Form
				//apn Phildya js cha adi ek Rojpay chi CdN Lavaychi
				//option Strore Karnastahi
				let options={
					key:'rzp_test_0YowFv1OI7umEn',
					amout:response.amount,
					currency :'INR',
					name:'Online Piligriimage',
					description:'Donation',
					image:'',
					order_id:response.id,
					handler:function(response){
						console.log(response.razorpay_payment_id)
						console.log(response.razorpay_order_id)
						console.log(response.razorpay_signature)
						console.log("payment succesful!!")
					
						//alert("congarts !!payment syyyccs")
						//phileda Update Stus KArycher Palaya maun Function
						//pass To The Function Here Will Khali Apn Function Kele Ahe
						updatePayementServer(response.razorpay_order_id,"paid");
						
					},
					prefill:{
						name:"",
						email:"",
						contact:"",

					},
                   notes:{
					address:"Aniket Temgire"
				   },
				   theme:{
					color:"#3399cc",
				   },
				};
				let rzp=new Razorpay(options);
                 rzp.on("payment.failed",function(response){
					console(response.error.code);
					console(response.error.description);
					console(response.error.source);
					console(response.error.step);
					console(response.error.reason);
					console(response.error.metadata.order_id);
					console(response.error.payment_id);
					//alert("Payment Fail");
					swal("Failed!", "Payment Failed Due To Some Resone!", "error");

				 });

				rzp.open();
			}
		},
		error:function(error){
			//invoked when error
			console.log(error);
			
		},

	});

};


function updatePayementServer(order_id,status)
{
	$.ajax({
        //create url to sent the request to the server with data
		url:"/user/update_order",
		// ha data ahe apla
		data:JSON.stringify({order_id:order_id,status:status}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response)
		{

			swal("Good job!", "Payment Successful!", "success");

		},
		error:function(error){
			swal("Failed!", "Payment SuccesFul But We Did Not Capture Contact As Sooon As!", "error");
		},
	});
	

}



const fligthpaymentStart=()=>{
    console.log("stared");
	let amout=$("#add-sum").html();
		let date=$("#dc").val();
		let noseat=$("#num-one").val();
		
		let busId=$("#buid").val();
	
		
		
	
	console.log(amout);
	//amout jar empty kiva null asel tar alert yenar ani retur manje tynatar kaich nai honar
	if(amout=='' || amout==null ||amout <=0 || date=='' || date==null || noseat=='' || noseat==null|| busId=='' || busId==null){
		//alert("amout is requried");
		swal("Something Wrong!", "Please Enter Correct Deatil!", "error");
		return ;
	}
	//will use ajax to creta order to server by using jqury version min.js 


	$.ajax(
		{
        //create url to sent the request to the server with data
		url:'/user/create_Flightorder',
	
		
					
		// ha data ahe apla
		data:JSON.stringify({amout:amout,date:date,noseat:noseat,busId:busId,info:'order_request'}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response){
			//this function invoked succes 
			console.log(response);
			//check if Resonse Apn handle Mahuds Return kel aahe tho Ala Ka Manun
			if(response.status=='created'){
				//asel tar apn form Open Karun Shakto
				//open Payment Form
				//apn Phildya js cha adi ek Rojpay chi CdN Lavaychi
				//option Strore Karnastahi
				let options={
					key:'rzp_test_0YowFv1OI7umEn',
					amout:response.amount,
					currency :'INR',
					name:'MyTrip Booking',
					description:'Donation',
					image:'',
					order_id:response.id,
					handler:function(response){
						console.log(response.razorpay_payment_id)
						console.log(response.razorpay_order_id)
						console.log(response.razorpay_signature)
						console.log("payment succesful!!")
					
						//alert("congarts !!payment syyyccs")
						//phileda Update Stus KArycher Palaya maun Function
						//pass To The Function Here Will Khali Apn Function Kele Ahe
						FlightupdatePayementServer(response.razorpay_order_id,"paid");
						
					},
					prefill:{
						name:"",
						email:"",
						contact:"",

					},
                   notes:{
					address:"Aniket Temgire"
				   },
				   theme:{
					color:"#3399cc",
				   },
				};
				let rzp=new Razorpay(options);
                 rzp.on("payment.failed",function(response){
					console(response.error.code);
					console(response.error.description);
					console(response.error.source);
					console(response.error.step);
					console(response.error.reason);
					console(response.error.metadata.order_id);
					console(response.error.payment_id);
					//alert("Payment Fail");
					swal("Failed!", "Payment Failed Due To Some Resone!", "error");

				 });

				rzp.open();
			}
		},
		error:function(error){
			//invoked when error
			console.log(error);
			
		},

	});

};


function FlightupdatePayementServer(order_id,status)
{
	$.ajax({
        //create url to sent the request to the server with data
		url:"/user/update_Fligthorder",
		// ha data ahe apla
		data:JSON.stringify({order_id:order_id,status:status}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response)
		{

			swal("Good job!", "Payment Successful!", "success");

		},
		error:function(error){
			swal("Failed!", "Payment SuccesFul But We Did Not Capture Contact As Sooon As!", "error");
		},
	});
	

}
const TrainpaymentStart=()=>{
    console.log("stared");
	let amout=$("#add-sum").html();
		let date=$("#dc").val();
		let noseat=$("#num-one").val();
		
		let busId=$("#buid").val();
	
		
		
	
	console.log(amout);
	//amout jar empty kiva null asel tar alert yenar ani retur manje tynatar kaich nai honar
	if(amout=='' || amout==null ||amout <=0 || date=='' || date==null || noseat=='' || noseat==null|| busId=='' || busId==null){
		//alert("amout is requried");
		swal("Something Wrong!", "Please Enter Correct Deatil!", "error");
		return ;
	}
	//will use ajax to creta order to server by using jqury version min.js 


	$.ajax(
		{
        //create url to sent the request to the server with data
		url:'/user/create_Trainorder',
	
		
					
		// ha data ahe apla
		data:JSON.stringify({amout:amout,date:date,noseat:noseat,busId:busId,info:'order_request'}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response){
			//this function invoked succes 
			console.log(response);
			//check if Resonse Apn handle Mahuds Return kel aahe tho Ala Ka Manun
			if(response.status=='created'){
				//asel tar apn form Open Karun Shakto
				//open Payment Form
				//apn Phildya js cha adi ek Rojpay chi CdN Lavaychi
				//option Strore Karnastahi
				let options={
					key:'rzp_test_0YowFv1OI7umEn',
					amout:response.amount,
					currency :'INR',
					name:'MyTrip Booking',
					description:'Donation',
					image:'',
					order_id:response.id,
					handler:function(response){
						console.log(response.razorpay_payment_id)
						console.log(response.razorpay_order_id)
						console.log(response.razorpay_signature)
						console.log("payment succesful!!")
					
						//alert("congarts !!payment syyyccs")
						//phileda Update Stus KArycher Palaya maun Function
						//pass To The Function Here Will Khali Apn Function Kele Ahe
						TrainupdatePayementServer(response.razorpay_order_id,"paid");
						
					},
					prefill:{
						name:"",
						email:"",
						contact:"",

					},
                   notes:{
					address:"Aniket Temgire"
				   },
				   theme:{
					color:"#3399cc",
				   },
				};
				let rzp=new Razorpay(options);
                 rzp.on("payment.failed",function(response){
					console(response.error.code);
					console(response.error.description);
					console(response.error.source);
					console(response.error.step);
					console(response.error.reason);
					console(response.error.metadata.order_id);
					console(response.error.payment_id);
					//alert("Payment Fail");
					swal("Failed!", "Payment Failed Due To Some Resone!", "error");

				 });

				rzp.open();
			}
		},
		error:function(error){
			//invoked when error
			console.log(error);
			
		},

	});

};


function TrainupdatePayementServer(order_id,status)
{
	$.ajax({
        //create url to sent the request to the server with data
		url:"/user/update_Trainorder",
		// ha data ahe apla
		data:JSON.stringify({order_id:order_id,status:status}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response)
		{

			swal("Good job!", "Payment Successful!", "success");

		},
		error:function(error){
			swal("Failed!", "Payment SuccesFul But We Did Not Capture Contact As Sooon As!", "error");
		},
	});
	

}


const hotelpaymentStart=()=>{
    console.log("stared");
	let amout=$("#add-sum").html();
		let date=$("#dc").val();
		let noseat=$("#num-one").val();
		
		let busId=$("#buid").val();
	
		
		
	
	console.log(amout);
	//amout jar empty kiva null asel tar alert yenar ani retur manje tynatar kaich nai honar
	if(amout=='' || amout==null ||amout <=0 || date=='' || date==null || noseat=='' || noseat==null|| busId=='' || busId==null){
		//alert("amout is requried");
		swal("Something Wrong!", "Please Enter Correct Deatil!", "error");
		return ;
	}
	//will use ajax to creta order to server by using jqury version min.js 


	$.ajax(
		{
        //create url to sent the request to the server with data
		url:'/user/create_hotelorder',
	
		
					
		// ha data ahe apla
		data:JSON.stringify({amout:amout,date:date,noseat:noseat,busId:busId,info:'order_request'}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response){
			//this function invoked succes 
			console.log(response);
			//check if Resonse Apn handle Mahuds Return kel aahe tho Ala Ka Manun
			if(response.status=='created'){
				//asel tar apn form Open Karun Shakto
				//open Payment Form
				//apn Phildya js cha adi ek Rojpay chi CdN Lavaychi
				//option Strore Karnastahi
				let options={
					key:'rzp_test_0YowFv1OI7umEn',
					amout:response.amount,
					currency :'INR',
					name:'MyTrip Booking',
					description:'Donation',
					image:'',
					order_id:response.id,
					handler:function(response){
						console.log(response.razorpay_payment_id)
						console.log(response.razorpay_order_id)
						console.log(response.razorpay_signature)
						console.log("payment succesful!!")
					
						//alert("congarts !!payment syyyccs")
						//phileda Update Stus KArycher Palaya maun Function
						//pass To The Function Here Will Khali Apn Function Kele Ahe
						hotelupdatePayementServer(response.razorpay_order_id,"paid");
						
					},
					prefill:{
						name:"",
						email:"",
						contact:"",

					},
                   notes:{
					address:"Aniket Temgire"
				   },
				   theme:{
					color:"#3399cc",
				   },
				};
				let rzp=new Razorpay(options);
                 rzp.on("payment.failed",function(response){
					console(response.error.code);
					console(response.error.description);
					console(response.error.source);
					console(response.error.step);
					console(response.error.reason);
					console(response.error.metadata.order_id);
					console(response.error.payment_id);
					//alert("Payment Fail");
					swal("Failed!", "Payment Failed Due To Some Resone!", "error");

				 });

				rzp.open();
			}
		},
		error:function(error){
			//invoked when error
			console.log(error);
			
		},

	});

};


function hotelupdatePayementServer(order_id,status)
{
	$.ajax({
        //create url to sent the request to the server with data
		url:"/user/update_hotelorder",
		// ha data ahe apla
		data:JSON.stringify({order_id:order_id,status:status}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response)
		{

			swal("Good job!", "Payment Successful!", "success");

		},
		error:function(error){
			swal("Failed!", "Payment SuccesFul But We Did Not Capture Contact As Sooon As!", "error");
		},
	});
	

}



const PacakgepaymentStart=()=>{
    console.log("stared");
	let amout=$("#add-sum").html();
		let date=$("#dc").val();
		let noseat=$("#num-one").val();
		let adress=$("#adr").val();
		let pid=$("#pid").val();
	
		
		
	
	console.log(amout);
	//amout jar empty kiva null asel tar alert yenar ani retur manje tynatar kaich nai honar
	if(amout=='' || amout==null ||amout <=0 || date=='' || date==null || noseat=='' || noseat==null|| adress=='' || adress==null || pid=='' || pid==null){
		//alert("amout is requried");
		swal("Something Wrong!", "Please Enter Deatil!", "error");
		return ;
	}
	//will use ajax to creta order to server by using jqury version min.js 


	$.ajax(
		{
        //create url to sent the request to the server with data
		url:'/user/create_packorder',
	
		
					
		// ha data ahe apla
		data:JSON.stringify({amout:amout,date:date,noseat:noseat,adress:adress,pid:pid,info:'order_request'}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response){
			//this function invoked succes 
			console.log(response);
			//check if Resonse Apn handle Mahuds Return kel aahe tho Ala Ka Manun
			if(response.status=='created'){
				//asel tar apn form Open Karun Shakto
				//open Payment Form
				//apn Phildya js cha adi ek Rojpay chi CdN Lavaychi
				//option Strore Karnastahi
				let options={
					key:'rzp_test_0YowFv1OI7umEn',
					amout:response.amount,
					currency :'INR',
					name:'Online Piligriimage',
					description:'Donation',
					image:'',
					order_id:response.id,
					handler:function(response){
						console.log(response.razorpay_payment_id)
						console.log(response.razorpay_order_id)
						console.log(response.razorpay_signature)
						console.log("payment succesful!!")
					
						//alert("congarts !!payment syyyccs")
						//phileda Update Stus KArycher Palaya maun Function
						//pass To The Function Here Will Khali Apn Function Kele Ahe
						PackageupdatePayementServer(response.razorpay_order_id,"paid");
						
					},
					prefill:{
						name:"",
						email:"",
						contact:"",

					},
                   notes:{
					address:"Aniket Temgire"
				   },
				   theme:{
					color:"#3399cc",
				   },
				};
				let rzp=new Razorpay(options);
                 rzp.on("payment.failed",function(response){
					console(response.error.code);
					console(response.error.description);
					console(response.error.source);
					console(response.error.step);
					console(response.error.reason);
					console(response.error.metadata.order_id);
					console(response.error.payment_id);
					//alert("Payment Fail");
					swal("Failed!", "Payment Failed Due To Some Resone!", "error");

				 });

				rzp.open();
			}
		},
		error:function(error){
			//invoked when error
			console.log(error);
			
		},

	});

};


function PackageupdatePayementServer(order_id,status)
{
	$.ajax({
        //create url to sent the request to the server with data
		url:"/user/update_packorder",
		// ha data ahe apla
		data:JSON.stringify({order_id:order_id,status:status}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response)
		{

			swal("Good job!", "Payment Successful!", "success");

		},
		error:function(error){
			swal("Failed!", "Payment SuccesFul But We Did Not Capture Contact As Sooon As!", "error");
		},
	});
	

}

function caneleHotel(hotel_id){
	
	console.log(hotel_id);
	swal({
		  title: "Are you sure?",
		  text: "We Want Do Cancle This Hotel!",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
		    window.location="/user/canclehotel/"+hotel_id;
		  } else {
		    swal("Your Saved!");
		  }
		});
	
}
function caneleBus(bus_id){
	
	
	swal({
		  title: "Are you sure?",
		  text: "We Want Do Cancle This Bus!",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
		    window.location="/user/cancleBus/"+bus_id;
		  } else {
		    swal("Your Saved!");
		  }
		});
	
}
function caneleTrain(tid){
	
	console.log(tid);
	swal({
		  title: "Are you sure?",
		  text: "We Want Do Cancle This Train!",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
		    window.location="/user/cancletrain/"+tid;
		  } else {
		    swal("Your Saved!");
		  }
		});
	
}
function caneleFligtht(fid){
	
	console.log(fid);
	swal({
		  title: "Are you sure?",
		  text: "We Want Do Cancle This Fligtht!",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
		    window.location="/user/cancleflight/"+fid;
		  } else {
		    swal("Your Saved!");
		  }
		});
	
}
function canelePackage(pid){
	
	console.log(pid);
	swal({
		  title: "Are you sure?",
		  text: "We Want Do Cancle This pacakge!",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
		    window.location="/user/canclpackage/"+pid;
		  } else {
		    swal("Your Saved!");
		  }
		});
	
}