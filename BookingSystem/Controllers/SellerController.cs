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
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> SetMeAvailableAsync(TimeFrameDto timeFrameDto)
        {
            var userId =_userManager.GetUserId(HttpContext.User);
            try
            {
                await _sellerService.SetMeAvailableAsync(userId, timeFrameDto.GetTimeFrame());
            } catch (InvalidTimeFrameException ex)
            {
                ModelState.AddModelError("CustomError", ex.Message);
            }
            TempData["success"] = "Added to calendar";
            return View("Index",timeFrameDto);
        }

    }
}
