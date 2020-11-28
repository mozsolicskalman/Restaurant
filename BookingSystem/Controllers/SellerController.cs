using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using BookingSystem.DTOs;
using Microsoft.AspNetCore.Identity;
using BookingSystem.Models;
using BookingSystem.Services.Interfaces;
using BookingSystem.Services.Exceptions;

namespace BookingSystem.Controllers
{
    [Authorize(Roles = "Seller")]
    public class SellerController : Controller
    {
        private UserManager<ApplicationUser> _userManager;

        private ISellerService _sellerService;

        public SellerController(UserManager<ApplicationUser> userManager, ISellerService sellerService)
        {
            this._userManager = userManager;
            this._sellerService = sellerService;
        }

        public ActionResult Index()
        {
            return Redirect("Availability");
        }

        public async Task<ActionResult> Availability()
        {
            ViewData["seller"] = await _sellerService.GetSellerAsync(_userManager.GetUserId(HttpContext.User));
            return View();
        }

        public async Task<ActionResult> Services()
        {
            ViewData["seller"] = await _sellerService.GetSellerAsync(_userManager.GetUserId(HttpContext.User));
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> SetMeAvailableAsync(TimeFrameDto timeFrameDto)
        {
            var userId = _userManager.GetUserId(HttpContext.User);
            try
            {
                await _sellerService.SetMeAvailableAsync(userId, timeFrameDto.GetTimeFrame());
            }
            catch (InvalidTimeFrameException ex)
            {
                ModelState.AddModelError("CustomError", ex.Message);
            }

            TempData["success"] = "Added to calendar";
            ViewData["seller"] = await _sellerService.GetSellerAsync(_userManager.GetUserId(HttpContext.User));
            return View("Availability");
        }

        public async Task<ActionResult> AcceptAppointmentRequestAsync(string appointmentId)
        {
            var userId = _userManager.GetUserId(HttpContext.User);
            TempData["AcceptAppointmentMessage"] = "You have accepted the appointment!";
            try
            {
                await _sellerService.AcceptAppointmentRequestAsync(userId, appointmentId);
            }
            catch (Exception ex)
            {
                TempData["AcceptAppointmentMessage"] = ex.Message;
            }

            return View("AcceptAppointmentResult");
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> AddNewServiceAsync(ServiceDto serviceDto)
        {
            var userId = _userManager.GetUserId(HttpContext.User);

            await _sellerService.AddProvidedServiceAsync(userId, serviceDto.GetService());

            TempData["serviceAddSuccess"] = "Added to services";
            ViewData["seller"] = await _sellerService.GetSellerAsync(_userManager.GetUserId(HttpContext.User));
            return View("Services");
        }

    }
}
