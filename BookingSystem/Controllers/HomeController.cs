using BookingSystem.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using BookingSystem.Services.Interfaces;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Authorization;

namespace BookingSystem.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly ISellerService _sellerService;
        private readonly UserManager<ApplicationUser> _userManager;

        public HomeController(ILogger<HomeController> logger, ISellerService sellerService, UserManager<ApplicationUser> userManager)
        {
            _logger = logger;
            _sellerService = sellerService;
            _userManager = userManager;
        }

        public IActionResult Index()
        {
            if (HttpContext.User.IsInRole("Seller"))
            {
                return RedirectToAction("WelcomeSeller");
            }
            else if (HttpContext.User.IsInRole("Customer"))
            {
                return RedirectToAction("WelcomeCustomer");
            }

            return View();
        }

        [Authorize(Roles = "Seller")]
        public async Task<IActionResult> WelcomeSeller()
        {
            ViewData["seller"] = await _sellerService.GetSellerAsync(_userManager.GetUserId(HttpContext.User));
            return View();
        }

        [Authorize(Roles = "Customer")]
        public IActionResult WelcomeCustomer()
        {
            //TempData["customer"] = await _customerService.GetCustomerAsync(_userManager.GetUserId(HttpContext.User));
            return View();
        }

        public IActionResult About()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
