using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
using BookingSystem.Models;

namespace BookingSystem.DTOs
{
    public class ServiceDto
    {
        [Required]
        public string Type { get; set; }

        [Required]
        public double DurrationMinutes { get; set; }

        public Service GetService()
        {
            return new Service
            {
                DurrationMinutes = this.DurrationMinutes,
                Type = this.Type,
                Id = Guid.NewGuid().ToString()
            };
        }
    }
}
